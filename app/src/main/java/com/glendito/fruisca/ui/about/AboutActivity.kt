package com.glendito.fruisca.ui.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glendito.fruisca.R
import com.glendito.fruisca.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { finish() }
    }
}