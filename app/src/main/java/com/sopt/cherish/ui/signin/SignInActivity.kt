package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.ui.main.MainActivity

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}