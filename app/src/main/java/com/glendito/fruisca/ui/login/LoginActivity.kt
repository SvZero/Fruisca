package com.glendito.fruisca.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.R
import com.glendito.fruisca.app.App
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.databinding.ActivityLoginBinding
import com.glendito.fruisca.ui.chooserole.ChooseRoleActivity
import com.glendito.fruisca.ui.forgotpassword.ForgotPasswordActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    @Inject
    lateinit var viewModelFactory: LoginViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        viewModel.isSuccessLogin.observe(this) { isSuccessLogin ->
            if (isSuccessLogin) {
                binding.btnLogin.revertAnimation()
                Toast.makeText(this, "Berhasil login", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                binding.btnLogin.revertAnimation()
                Toast.makeText(this, "Email atau Password salah", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        binding.btnBack.setOnClickListener { finish() }
        binding.btnLogin.setOnClickListener {
            val password = binding.edtPassword.text.toString()
            val email = binding.edtEmail.text.toString()

            when {
                email.isEmpty() -> binding.edtEmail.error = "Harus diisi"
                password.isEmpty() -> binding.edtPassword.error = "Harus diisi"
                else -> {
                    binding.btnLogin.startAnimation()
                    viewModel.login(email, password)
                }
            }
        }
    }
}