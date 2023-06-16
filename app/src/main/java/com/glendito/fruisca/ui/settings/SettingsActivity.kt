package com.glendito.fruisca.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.ActivitySettingsBinding
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.ui.landing.LandingActivity
import com.glendito.fruisca.ui.about.AboutActivity
import com.glendito.fruisca.ui.changepassword.ChangePasswordActivity
import com.glendito.fruisca.ui.guide.GuideActivity
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    @Inject
    lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as App).appComponent.inject(this)

        binding.btnBack.setOnClickListener { finish() }
        binding.btnLogout.setOnClickListener {
            userPreferences.reset()
            Toast.makeText(this, "Berhasil logout", Toast.LENGTH_SHORT).show()
            startActivity(
                Intent(this, LandingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            )
        }
        binding.imgAbout.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
        binding.txtAbout.setOnClickListener { startActivity(Intent(this, AboutActivity::class.java)) }
        binding.imgChangePassword.setOnClickListener { startActivity(Intent(this, ChangePasswordActivity::class.java)) }
        binding.txtChangePassword.setOnClickListener { startActivity(Intent(this, ChangePasswordActivity::class.java)) }
        binding.imgGuide.setOnClickListener { startActivity(Intent(this, GuideActivity::class.java)) }
        binding.txtGuide.setOnClickListener { startActivity(Intent(this, GuideActivity::class.java)) }
    }

}