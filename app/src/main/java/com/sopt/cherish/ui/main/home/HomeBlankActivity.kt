package com.sopt.cherish.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityHomeBlankBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.HomeBlankViewModel


class HomeBlankActivity : AppCompatActivity() {
    private val viewModel: HomeBlankViewModel by viewModels { Injection.provideHomeBlankViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBlankBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home_blank)
        binding.homeBlankActivity = this
        binding.homeBlankViewModel = viewModel

        viewModel.userId = intent.getIntExtra("userId", -1)
    }

    fun navigatePhone() {
        val intent = Intent(this@HomeBlankActivity, EnrollmentPhoneActivity::class.java)
        intent.putExtra("userId", viewModel.userId)
        startActivity(intent)
    }
}