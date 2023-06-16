package com.glendito.fruisca.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glendito.fruisca.R
import com.glendito.fruisca.databinding.ActivityWelcomeBinding
import com.glendito.fruisca.ui.home.HomeActivity

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}