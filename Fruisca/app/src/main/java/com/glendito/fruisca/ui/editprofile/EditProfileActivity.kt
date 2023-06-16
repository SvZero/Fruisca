package com.glendito.fruisca.ui.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.ActivityEditProfileBinding
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var viewModel: EditProfileViewModel

    @Inject
    lateinit var viewModelFactory: EditProfileViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[EditProfileViewModel::class.java]
        viewModel.getProfile()

        viewModel.user.observe(this) { user ->
            binding.edtName.setText(user.name)
            binding.edtAddress.setText(user.address)
            binding.edtPhone.setText(user.phone)
        }

        viewModel.isEdited.observe(this) {
            Toast.makeText(this, "Berhasil edit profile", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnEditProfile.setOnClickListener {
            val name = binding.edtName.text.toString()
            val phone = binding.edtPhone.text.toString()
            val address = binding.edtAddress.text.toString()

            when {
                name.isEmpty() -> binding.edtName.error = "Harus diisi"
                phone.isEmpty() -> binding.edtPhone.error = "Harus diisi"
                address.isEmpty() -> binding.edtAddress.error = "Harus diisi"
                else -> viewModel.editProfile(name, phone, address)
            }
        }
    }
}