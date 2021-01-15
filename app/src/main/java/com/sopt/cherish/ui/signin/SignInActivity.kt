package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.EditUserReq
import com.sopt.cherish.remote.api.EditUserRes
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.main.home.HomeBlankActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val requestData = RetrofitBuilder

    private val viewModel: MainViewModel by viewModels { Injection.provideMainViewModelFactory() }

    // todo : 1. Login이 실패할 경우 , 2. 네트워킹이 제대로 되어 있지 않은 경우 여기서 판단
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
                                viewModel.userId.value = response.body()?.editUserData?.userId
                                viewModel.fetchUsers()
                                // 여기서 분기가 들어가야함 , 이게 최선입니까? 송훈기 씨?
                                if (viewModel.cherishUsers.value?.userData?.totalUser == null) {
                                    val intent =
                                        Intent(this@SignInActivity, HomeBlankActivity::class.java)
                                    intent.putExtra("userId", response.body()?.editUserData?.userId)
                                    intent.putExtra(
                                        "userNickname",
                                        response.body()?.editUserData?.userNickName
                                    )
                                    startActivity(intent)
                                } else {
                                    val mainActivityIntent =
                                        Intent(this@SignInActivity, MainActivity::class.java)
                                    mainActivityIntent.putExtra(
                                        "userId",
                                        response.body()?.editUserData?.userId
                                    )
                                    mainActivityIntent.putExtra(
                                        "userNickname",
                                        response.body()?.editUserData?.userNickName
                                    )
                                    startActivity(mainActivityIntent)
                                }

                            }
                    }
                })
    }
}