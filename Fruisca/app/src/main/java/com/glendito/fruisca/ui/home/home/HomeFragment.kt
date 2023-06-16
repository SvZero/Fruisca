package com.glendito.fruisca.ui.home.home

import android.app.Activity
import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import com.glendito.fruisca.R
import com.glendito.fruisca.databinding.FragmentHomeBinding
import com.glendito.fruisca.ml.Model
import com.glendito.fruisca.ui.resultscan.ResultScanActivity
import com.glendito.fruisca.ui.settings.SettingsActivity
import com.glendito.fruisca.utils.PermissionUtils
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileNotFoundException
import java.util.UUID

class HomeFragment : Fragment() {

    private val ContentResolverl: ContentResolver? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var imgPath: Uri? = null
    lateinit var bitmap: Bitmap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        val dialog = Dialog(requireContext()).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_scan)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(true)
        }

        binding.btnAddFruit.setOnClickListener {
            dialog.findViewById<ImageView>(R.id.btnGallery).setOnClickListener {
                PermissionUtils.showReadExternalStorage(requireActivity()) {
                    val intent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(intent, 0)
                }
                dialog.cancel()
            }

            dialog.findViewById<ImageView>(R.id.btnCamera).setOnClickListener {
                PermissionUtils.showWriteExternalStorage(requireActivity()) {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val values = ContentValues()
                    val random = UUID.randomUUID().toString()
                    values.put(MediaStore.Images.Media.TITLE, random)
                    values.put(MediaStore.Images.Media.DESCRIPTION, random)
                    imgPath = activity?.contentResolver?.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        values
                    )
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imgPath)
                    startActivityForResult(intent, 1)
                }
                dialog.cancel()
            }

            dialog.show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                val targetUri = data?.data
                imgPath = targetUri
                try {
                    targetUri?.let {

                        requireContext().startActivity(
                            Intent(requireContext(), ResultScanActivity::class.java).apply {
                                putExtra("img", imgPath.toString())
                            }
                        )
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            } else if (requestCode == 1) {
                try {
                    imgPath?.let {
                        requireContext().startActivity(
                            Intent(requireContext(), ResultScanActivity::class.java).apply {
                                putExtra("img", imgPath.toString())
                            }
                        )
                    }
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}