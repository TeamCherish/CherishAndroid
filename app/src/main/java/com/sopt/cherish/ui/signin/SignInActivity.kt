package com.sopt.cherish.ui.signin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sopt.cherish.databinding.ActivitySignInBinding
import com.sopt.cherish.remote.api.AuthAPI
import com.sopt.cherish.remote.api.EditUserReq
import com.sopt.cherish.remote.api.EditUserRes
import com.sopt.cherish.remote.model.RequestSigninData
import com.sopt.cherish.remote.model.ResponseSigninData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.main.MainActivity
import com.sopt.cherish.util.MyApplication
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
            val email_user = binding.editTextTextPersonName.text.toString()
            val password_user = binding.editTextTextPassword.text.toString()

            val intent =
                Intent(this@SignInActivity, MainActivity::class.java)
            startActivity(intent)

           /* requestData.signinAPI.singinuser(
                EditUserReq(
                    email = email_user,
                    password = password_user
                )
            )
                .enqueue(
                    object : Callback<EditUserRes> {
                        override fun onFailure(call: Call<EditUserRes>, t: Throwable) {
                            Log.d("통신 실패", t.toString())
                        }

                        override fun onResponse(
                            call: Call<EditUserRes>,
                            response: Response<EditUserRes>
                        ) {
                            Log.d("success", "성공")
                            Log.d("zzzzzzz",response.isSuccessful.toString())
                            response.takeIf {
                                it.isSuccessful

                            }?.body()
                                ?.let { it ->
                                    val intent =
                                        Intent(this@SignInActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    Log.d("data success!", it.userId.toString())
                                }
                        }
                    }
                )
*/

        }
    }
}