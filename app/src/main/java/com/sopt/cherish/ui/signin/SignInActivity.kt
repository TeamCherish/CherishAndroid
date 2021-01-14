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

    // todo : 1. Login이 실패할 경우 , 2. 네트워킹이 제대로 되어 있지 않은 경우 여기서 판단
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            // 디버깅용 startActivity
/*            val intent =
                Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)*/

            val email = binding.editTextTextPersonName.text.toString()
            val pw = binding.editTextTextPassword.text.toString()
            signIn(email, pw)
        }
    }

    private fun signIn(email: String, password: String) {
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
                                val intent =
                                    Intent(this@SignInActivity, MainActivity::class.java)
                                intent.putExtra("userId", response.body()?.editUserData?.userId)
                                intent.putExtra(
                                    "userNickname",
                                    response.body()?.editUserData?.userNickName
                                )
                                startActivity(intent)
                            }
                    }
                })
    }
}