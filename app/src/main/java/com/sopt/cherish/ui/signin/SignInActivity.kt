package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.RetrofitBuilder
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

            val email_user= binding.editTextTextPersonName.text.toString()
            val password_user= binding.editTextTextPassword.text.toString()

            val body = RequestSigninData(email =email_user,password = password_user)


            val call: Call<ResponseSigninData> = RetrofitBuilder.retrofitService.signIn(body)
            call.enqueue(object : Callback<ResponseSigninData> {
                override fun onFailure(call: Call<ResponseSigninData>, t: Throwable) {
                    Log.d("response", "실패")
                }
                override fun onResponse(
                    call: Call<ResponseSigninData>,
                    response: Response<ResponseSigninData>
                ) {
                    Log.d("responseㄴㄴㄴ", "성공")

                    response.takeIf { it.isSuccessful }
                        ?.body()
                        ?.let {it->

                            MyApplication.mySharedPreferences.setValue("token", it.data.UserId.toString())
                            Log.d("signin", "로그인 성공")
                            val intent = Intent(this@SignInActivity, MainActivity::class.java)
                            startActivity(intent)

                        } ?: Toast.makeText(this@SignInActivity, "error", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}