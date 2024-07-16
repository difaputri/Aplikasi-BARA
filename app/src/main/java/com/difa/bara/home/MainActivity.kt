package com.difa.bara.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.difa.bara.R
import com.difa.bara.databinding.ActivityMainBinding
import com.difa.bara.settings.SettingsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())
        binding.bottomNavigation.setOnApplyWindowInsetsListener(null)
        binding.bottomNavigation.setPadding(0,0,0,0)

        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.nav_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_view, fragment)
            .commit()
    }
}