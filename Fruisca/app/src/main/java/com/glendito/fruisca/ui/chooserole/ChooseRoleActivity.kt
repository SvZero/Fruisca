package com.glendito.fruisca.ui.chooserole

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.R
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.ActivityChooseRoleBinding
import com.glendito.fruisca.ui.WelcomeActivity
import javax.inject.Inject

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChooseRoleBinding
    private lateinit var viewModel: ChooseRoleViewModel

    @Inject
    lateinit var viewModelFactory: ChooseRoleViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[ChooseRoleViewModel::class.java]
        viewModel.isUpdated.observe(this) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            finish()
        }

        binding.btnFarmer.setOnClickListener {
            viewModel.setRole("Petani")
        }

        binding.btnPublic.setOnClickListener {
            viewModel.setRole("Umum")
        }

        binding.btnStudent.setOnClickListener {
            viewModel.setRole("Pelajar/Mahasiswa")
        }
    }
}