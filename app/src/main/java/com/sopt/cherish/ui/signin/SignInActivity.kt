package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.EditUserReq
import com.sopt.cherish.remote.api.EditUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val requestData = RetrofitBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {

            val email = binding.editTextTextPersonName.text.toString()
            val pw = binding.editTextTextPassword.text.toString()

            Log.d("email", email)
            Log.d("pw", pw)
            val intent =
                Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)
            /*requestData.authAPI.postLogin(EditUserReq(email, pw))
                .enqueue(
                    object : Callback<EditUserRes> {
                        override fun onFailure(call: Call<EditUserRes>, t: Throwable) {
                            Log.d("통신 실패", t.toString())
                        }

                        override fun onResponse(
                            call: Call<EditUserRes>,
                            response: Response<EditUserRes>
                        ) {
                            Log.d("success", response.body().toString())
                            response.takeIf {
                                it.isSuccessful
                            }?.body()
                                ?.let { it ->
                                    Log.d("isSuccess", response.body().toString())
                                    val intent =
                                        Intent(this@SignInActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                        }
                    })*/
        }
    }
}