package com.sopt.cherish.ui.main.onboarding


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityOnboardingBinding

class OnBoardingActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityOnboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)

        setFragment(onboardingFragment())

        setContentView(binding.root)

    }

    fun setFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.onBoardingFragment, fragment)
        transaction.commit()
    }
}