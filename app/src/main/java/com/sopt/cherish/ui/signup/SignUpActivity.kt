package com.sopt.cherish.ui.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivitySignUpBinding.inflate(layoutInflater)

        setSupportActionBar(binding.signUpToolBar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        setContentView(R.layout.activity_sign_up)
    }
}