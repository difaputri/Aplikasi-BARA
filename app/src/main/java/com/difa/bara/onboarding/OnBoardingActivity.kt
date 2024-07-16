package com.difa.bara.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.difa.bara.R
import com.difa.bara.databinding.ActivityOnBoardingBinding
import com.difa.bara.welcome.WelcomeActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private val adapter by lazy { OnBoardingAdapter(this) }
    private val viewPager by lazy { ViewPager2(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setup()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 2) {
                    binding.btnNext.text = "Get Started"
                    binding.btnNext.setOnClickListener {
                        startActivity(Intent(this@OnBoardingActivity, WelcomeActivity::class.java))
                        finish()
                    }
                } else {
                    binding.btnNext.text = "Next"
                    binding.btnNext.setOnClickListener {
                        binding.viewPager.currentItem = position + 1
                    }
                }
            }
        })
    }

    private fun setup() {
        binding.viewPager.adapter = adapter
        adapter.differ.submitList(OnBoardingData.data)
    }
}