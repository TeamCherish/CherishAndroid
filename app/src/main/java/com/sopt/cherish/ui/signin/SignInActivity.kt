package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.MainApplication
import com.sopt.cherish.R
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.dialog.pwfinding.IDFindingDialog
import com.sopt.cherish.ui.dialog.signupdialog.SignUpDialogFragment
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.home.HomeBlankActivity
import com.sopt.cherish.ui.pwfinding.PwFindingActivity
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

        autoLogin()

        binding.loginBtn.setOnClickListener {
            val email = binding.editTextTextPersonName.text.toString()
            val pw = binding.editTextTextPassword.text.toString()
            signIn(email, pw)
        }

        binding.textView31.setOnClickListener {
            val signUpIntent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }

        binding.textView30.setOnClickListener {
            val pwFindingIntent = Intent(this@SignInActivity, PwFindingActivity::class.java)
            startActivity(pwFindingIntent)
        }

        binding.textView4.setOnClickListener {
            val dialog = IDFindingDialog(R.layout.fragment_id_finding_dialog)
            dialog.show(supportFragmentManager, "SignInActivity")
        }
    }

    private fun autoLogin() {
        if (MainApplication.sharedPreferenceController.getUserId() != null &&
            MainApplication.sharedPreferenceController.getUserNickname() != null &&
            MainApplication.sharedPreferenceController.getToken() != null
        ) {
            hasUser(
                MainApplication.sharedPreferenceController.getUserId()!!,
                MainApplication.sharedPreferenceController.getUserNickname()!!,
                MainApplication.sharedPreferenceController.getToken()!!
            )
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
                        if (response.body() == null) {
                            val dialog = SignUpDialogFragment(R.layout.fragment_sign_up_dialog)
                            dialog.show(supportFragmentManager, "SignInActivity")
                        }
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
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