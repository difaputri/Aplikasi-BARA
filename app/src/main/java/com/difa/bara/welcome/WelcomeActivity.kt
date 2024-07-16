package com.difa.bara.welcome

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.difa.bara.R
import com.difa.bara.databinding.ActivityWelcomeBinding
import com.difa.bara.login.LoginActivity
import com.difa.bara.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSignin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}