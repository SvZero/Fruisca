package com.glendito.fruisca.ui.landing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.app.App
import com.glendito.fruisca.databinding.ActivityLandingBinding
import com.glendito.fruisca.ui.WelcomeActivity
import com.glendito.fruisca.ui.chooserole.ChooseRoleActivity
import com.glendito.fruisca.ui.login.LoginActivity
import com.glendito.fruisca.ui.register.RegisterActivity
import javax.inject.Inject

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding
    private lateinit var viewModel: LandingViewModel

    @Inject
    lateinit var viewModelFactory: LandingViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[LandingViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.getEmail().isNotEmpty()) {
            val intentFlags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            viewModel.fetchRole()
            viewModel.role.observe(this) { role ->
                if (role.isNotEmpty()) {
                    startActivity(
                        Intent(this, WelcomeActivity::class.java).apply {
                            flags = intentFlags
                        }
                    )
                } else {
                    startActivity(
                        Intent(this, ChooseRoleActivity::class.java).apply {
                            flags = intentFlags
                        }
                    )
                }
            }
        }
    }
}