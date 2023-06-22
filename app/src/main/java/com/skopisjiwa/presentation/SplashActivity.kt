package com.skopisjiwa.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.skopisjiwa.MainActivity
import com.skopisjiwa.databinding.ActivitySplashBinding
import com.skopisjiwa.presentation.user.home.HomeActivity
import com.skopisjiwa.presentation.user.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private val SPLASH_SCREEN_DURATION = 2000L // 2 seconds
    private lateinit var binding: ActivitySplashBinding

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.isLogin.observe(this) { isLogin ->
                if(isLogin == true){
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }, SPLASH_SCREEN_DURATION)
    }
}