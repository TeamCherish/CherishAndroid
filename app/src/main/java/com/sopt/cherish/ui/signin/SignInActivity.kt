package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.MainApplication
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.home.HomeBlankActivity
import com.sopt.cherish.ui.signup.SignUpActivity
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

        Log.d("hello", MainApplication.sharedPreferenceController.getUserId().toString())
        Log.d("hello", MainApplication.sharedPreferenceController.getUserNickname().toString())
        if (MainApplication.sharedPreferenceController.getUserId() != null && MainApplication.sharedPreferenceController.getUserNickname() != null) {
            hasUser(
                MainApplication.sharedPreferenceController.getUserId()!!,
                MainApplication.sharedPreferenceController.getUserNickname()!!,
                MainApplication.sharedPreferenceController.getToken()!!
            )
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.editTextTextPersonName.text.toString()
            val pw = binding.editTextTextPassword.text.toString()
            signIn(email, pw)
        }

        binding.textView31.setOnClickListener {
            val signUpIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }

    private fun signIn(email: String, password: String) { //로그인 버튼 클릭
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
                                    response.body()!!.editUserData.userNickName,
                                    response.body()!!.editUserData.token
                                )
                            }
                    }
                })
    }

    private fun hasUser(userId: Int, userNickName: String, token: String) {
        var trigger: Boolean
        RetrofitBuilder.userAPI.hasUser(userId).enqueue(object : Callback<UserResult> {
            override fun onResponse(call: Call<UserResult>, response: Response<UserResult>) {
                if (response.isSuccessful) {
                    if (response.body()?.userData?.totalUser == 0) {
                        val blankHomeIntent =
                            Intent(this@SignInActivity, HomeBlankActivity::class.java)
                        blankHomeIntent.putExtra("userId", userId)
                        blankHomeIntent.putExtra("userNickname", userNickName)
                        MainApplication.sharedPreferenceController.apply {
                            setUserId(userId)
                            setUserNickname(userNickName)
                            setToken(token)
                        }
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
                        mainActivityIntent.putExtra(
                            "loginToken",
                            token
                        )
                        MainApplication.sharedPreferenceController.apply {
                            setUserId(userId)
                            setUserNickname(userNickName)
                            setToken(token)
                        }
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