package com.sopt.cherish.ui.pwfinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivityPwFindingBinding
import com.sopt.cherish.ui.signup.SignUpFirstFragment

class PwFindingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityPwFindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwFindingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeFragment()
    }

    private fun initializeFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_finding, SignUpFirstFragment()).commit()
    }
}