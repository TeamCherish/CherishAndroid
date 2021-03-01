package com.sopt.cherish.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySplashBinding
import com.sopt.cherish.ui.signin.SignInActivity

/**
 * Created on 2020-01-03 by SSong-develop
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_splash
        )

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }, 2000)
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fade_in)
        binding.imageView3.animation = fadeInAnimation
    }

}