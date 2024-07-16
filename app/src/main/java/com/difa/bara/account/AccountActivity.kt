package com.difa.bara.account

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.difa.bara.R
import com.difa.bara.databinding.ActivityAccountBinding
import com.difa.bara.storage.DataStoreKeys
import com.difa.bara.storage.user
import kotlinx.coroutines.launch

class AccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        getUserData()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun getUserData() {
        lifecycleScope.launch {
            applicationContext.user.data.collect { userData ->
                val name = userData[DataStoreKeys.NAME] ?: "none"
                binding.edName.editText?.setText(name)
                val email = userData[DataStoreKeys.EMAIL] ?: "none"
                binding.edEmail.editText?.setText(email)
            }
        }
    }
}