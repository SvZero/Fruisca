package com.glendito.fruisca.ui.resultscan

import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.R
import com.glendito.fruisca.app.App
import com.glendito.fruisca.database.entities.FruitEntity
import com.glendito.fruisca.databinding.ActivityResultScanBinding
import com.glendito.fruisca.ml.Model
import com.google.android.material.button.MaterialButton
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class ResultScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultScanBinding
    private lateinit var viewModel: ResultScanViewModel
    lateinit var bitmap: Bitmap

    @Inject
    lateinit var viewModelFactory: ResultScanViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var imageProcessor = ImageProcessor.Builder()
            .add(NormalizeOp(0.0f, 255.0f))
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .build()

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ResultScanViewModel::class.java]

        val imgUriString = intent?.getStringExtra("img").orEmpty()

        // Start Animation
        val rotate = RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 3000
        rotate.interpolator = LinearInterpolator()
        binding.imgLoading.startAnimation(rotate)

        //===============================================================
        var uri = Uri.parse(imgUriString)
        //bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        //bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        val options = BitmapFactory.Options()
        options.inSampleSize = 2 // Adjust the sample size as needed
        bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)!!

        var tensorImage = TensorImage(DataType.UINT8)
        tensorImage.load(bitmap)

        tensorImage = imageProcessor.process(tensorImage)

        val model = Model.newInstance(this)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(tensorImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        val confidences = outputFeature0.getFloatArray()

        var maxPos = 0
        var maxConfidence = 0f
        val float: Array<Float?> = arrayOfNulls(6)

        for (i in 0 until confidences.size) {
            if (confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
                float[i] = confidences[i]
            }
        }

        val classes = arrayOf("Apel Segar",
            "Pisang Segar",
            "Jeruk Segar",
            "Apel Busuk",
            "Pisang Busuk",
            "Jeruk Busuk")
        model.close()

        //===========================================================
        if (imgUriString.isEmpty()) {
            Toast.makeText(this, "Aplikasi tidak dapat mengenali objek. Coba arahkan kamera tepat mengenai objek.", Toast.LENGTH_SHORT).show()
            finish()
        }

        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_failed_scan)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
        }

        var angka = maxConfidence * 100
        val textAngka = "%.2f".format(angka)
        angka = textAngka.toFloat()
        angka = angka.roundToLong().toFloat()

        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.US)
        val fruit = FruitEntity(
            name = classes[maxPos],
            fresh = angka.toInt(),
            createdAt = dateFormat.format(Date()).toString(),
            health = if (classes[maxPos].contains("Segar")) {
                "Layak Konsumsi"
            } else if (classes[maxPos].contains("Busuk")) {
                "Tidak Layak Konsumsi"
            } else {
                ""
            },
            image = imgUriString
        )
        viewModel.uploadFruit(fruit)
        viewModel.scanResult.observe(this) { scanResult ->
            when (scanResult) {
                is ScanResult.Success -> {
                    if (classes[maxPos].contains("Segar")){
                        binding.txtSegar.text = "Presentase Kesegaran"
                        binding.txtHealth.text = "Buah ini layak konsumsi"
                        binding.progressBarG.visibility = View.VISIBLE
                        binding.progressBarR.visibility = View.GONE
                        binding.progressBarG.progress = angka.toInt()
                    } else if (classes[maxPos].contains("Busuk")){
                        binding.txtSegar.text = "Presentase Kebusukan"
                        binding.txtHealth.text = "Buah ini tidak layak konsumsi"
                        binding.progressBarR.visibility = View.VISIBLE
                        binding.progressBarG.visibility = View.GONE
                        binding.progressBarR.progress = angka.toInt()
                    }

                    binding.layoutAnalysis.visibility = View.VISIBLE
                    binding.layoutLoading.visibility = View.GONE
                    binding.txtFruit.text = classes[maxPos].toString()
                    //binding.progressBarR.progress = angka.toInt()
                    binding.txtProgress.text = "$textAngka%"

                    val imgUri = Uri.parse(imgUriString)
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imgUri))
                    binding.imgFruit.setImageBitmap(bitmap)
                }
                is ScanResult.Failed -> {
                    binding.txtLoadingStatus.text = "PEMINDAIAN GAGAL"
                    dialog.findViewById<MaterialButton>(R.id.btnRetry).setOnClickListener {
                        binding.txtLoadingStatus.text = "SEDANG MEMINDAI"
                        viewModel.uploadFruit(fruit)
                        dialog.cancel()
                    }
                    dialog.findViewById<MaterialButton>(R.id.btnBack).setOnClickListener {
                        dialog.cancel()
                        finish()
                    }
                    dialog.show()
                }
            }
        }

    }
}