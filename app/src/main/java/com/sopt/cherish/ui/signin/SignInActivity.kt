package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.model.RequestSigninData
import com.sopt.cherish.remote.model.ResponseSigninData
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.util.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {

            val email_user = binding.editTextTextPersonName.text.toString()
            val password_user = binding.editTextTextPassword.text.toString()

            val body = RequestSigninData(email = email_user, password = password_user)


        }
    }
}