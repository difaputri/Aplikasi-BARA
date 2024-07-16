package com.difa.bara.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.difa.bara.databinding.ActivitySplashScreenBinding
import com.difa.bara.home.MainActivity
import com.difa.bara.onboarding.OnBoardingActivity
import com.difa.bara.storage.DataStoreKeys
import com.difa.bara.storage.user
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            getUserData()
        }
    }

    private suspend fun getUserData() {
        user.data.collect { usrData ->
            val email = usrData[DataStoreKeys.EMAIL] ?: "none"
            val token = usrData[DataStoreKeys.TOKEN] ?: "none"

            runOnUiThread {
                if (email != "none" && token != "none") {
                    startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                    finish()
                    return@runOnUiThread
                } else {
                    startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java))
                    finish()
                    return@runOnUiThread
                }
            }
        }
    }
}