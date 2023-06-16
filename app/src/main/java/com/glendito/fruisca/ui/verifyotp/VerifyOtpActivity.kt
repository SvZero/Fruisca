package com.glendito.fruisca.ui.verifyotp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glendito.fruisca.databinding.ActivityVerifyOtpActivityBinding

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyOtpActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
    }
}