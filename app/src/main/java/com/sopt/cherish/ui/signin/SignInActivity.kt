package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.home.HomeBlankActivity
import com.sopt.cherish.util.extension.hideKeyboard
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
            signIn(email, pw)
        }
    }

    private fun signIn(email: String, password: String) {
        binding.editTextTextPassword.hideKeyboard()
        requestData.authAPI.postLogin(EditUserReq(email, password))
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
                                hasUser(
                                    response.body()?.editUserData?.userId!!,
                                    response.body()!!.editUserData.userNickName
                                )
                            }
                    }
                })
    }

    private fun hasUser(userId: Int, userNickName: String) {
        var trigger: Boolean
        RetrofitBuilder.userAPI.hasUser(userId).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if (response.isSuccessful) {
                    if (response.body()?.userData?.totalUser == 0) {
                        val blankHomeIntent =
                            Intent(this@SignInActivity, HomeBlankActivity::class.java)
                        blankHomeIntent.putExtra("userId", userId)
                        blankHomeIntent.putExtra("userNickname", userNickName)
                        startActivity(blankHomeIntent)

                        finish()
                    } else {
                        val mainActivityIntent =
                            Intent(this@SignInActivity, MainActivity::class.java)
                        mainActivityIntent.putExtra(
                            "userId",
                            userId
                        )
                        mainActivityIntent.putExtra(
                            "userNickname",
                            userNickName
                        )
                        startActivity(mainActivityIntent)
                        finish()
                    }
                }
            }

            override fun onFailure(call: Call<UserResult>, t: Throwable) {
                Log.d("통신 실패", t.toString())
            }
        })
    }
}