package com.glendito.fruisca.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.app.App
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.databinding.ActivityRegisterBinding
import com.glendito.fruisca.ui.login.LoginActivity
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    @Inject
    lateinit var viewModelFactory: RegisterViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[RegisterViewModel::class.java]

        viewModel.isRegistered.observe(this) { isRegistered ->
            if (isRegistered) {
                Toast.makeText(this, "Berhasil mendaftar!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnRegister.setOnClickListener {
            val name = binding.edtName.text.toString()
            val password = binding.edtPassword.text.toString()
            val email = binding.edtEmail.text.toString()
            val phone = binding.edtPhone.text.toString()
            val address = binding.edtAddress.text.toString()

            when {
                name.isEmpty() -> binding.edtName.error = "Harus diisi"
                email.isEmpty() -> binding.edtEmail.error = "Harus diisi"
                phone.isEmpty() -> binding.edtPhone.error = "Harus diisi"
                address.isEmpty() -> binding.edtAddress.error = "Harus diisi"
                password.isEmpty() -> binding.edtPassword.error = "Harus diisi"
                else -> {
                    binding.btnRegister.startAnimation()
                    viewModel.register(
                        UserEntity(
                            email,
                            name,
                            password,
                            phone,
                            address,
                            ""
                        )
                    )
                }
            }
        }
    }
}