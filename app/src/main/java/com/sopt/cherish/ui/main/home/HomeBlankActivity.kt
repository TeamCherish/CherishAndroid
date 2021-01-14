package com.sopt.cherish.ui.main.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.cherish.databinding.ActivityHomeBlankBinding
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity


class HomeBlankActivity : AppCompatActivity() {

    private lateinit var binding:ActivityHomeBlankBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBlankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val intent= Intent(this@HomeBlankActivity,EnrollmentPhoneActivity::class.java)
            startActivity(intent)
        }
    }
}