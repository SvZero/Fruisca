package com.glendito.fruisca.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.glendito.fruisca.R
import com.glendito.fruisca.databinding.ActivityHomeBinding
import com.glendito.fruisca.ui.home.history.HistoryFragment
import com.glendito.fruisca.ui.home.home.HomeFragment
import com.glendito.fruisca.ui.home.profile.ProfileFragment
import com.glendito.fruisca.ui.home.news.NewsFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        openFragment(HomeFragment.newInstance())

        binding.bottomNav.onItemSelectedListener = { _, menuItem, _ ->
            when (menuItem.id) {
                R.id.itemHome -> openFragment(HomeFragment.newInstance())
                R.id.itemHistory -> openFragment(HistoryFragment.newInstance())
                R.id.itemNews -> openFragment(NewsFragment.newInstance())
                R.id.itemProfile -> openFragment(ProfileFragment.newInstance())
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(binding.container.id, fragment)
            commit()
        }
    }
}