package com.glendito.fruisca.ui.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glendito.fruisca.R
import com.glendito.fruisca.databinding.ActivityForgotPasswordBinding
import com.glendito.fruisca.ui.verifyotp.VerifyOtpActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnNext.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            if (email.isEmpty()) {
                binding.edtEmail.error = "Harus diisi"
            } else {
                startActivity(
                    Intent(this, VerifyOtpActivity::class.java).apply {
                        putExtra("email", email)
                    }
                )
            }
        }
    }
}