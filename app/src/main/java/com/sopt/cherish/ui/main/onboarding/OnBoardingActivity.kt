package com.sopt.cherish.ui.main.onboarding

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityAboutcherishBinding
import com.sopt.cherish.databinding.ActivityOnboardingBinding
import com.sopt.cherish.databinding.FragmentOnboardingBinding
import com.sopt.cherish.ui.main.onboarding.adapter.OnBoardingViewPagerAdapter
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

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