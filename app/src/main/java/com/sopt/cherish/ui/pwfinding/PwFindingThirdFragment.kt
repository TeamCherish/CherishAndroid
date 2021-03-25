package com.sopt.cherish.ui.pwfinding

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPwFindingThirdBinding
import com.sopt.cherish.remote.api.RequestUpdatePasswordAPI
import com.sopt.cherish.remote.api.ResponseUpdatePasswordAPI
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import com.sopt.cherish.ui.signin.SignInActivity
import com.sopt.cherish.ui.signup.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PwFindingThirdFragment : Fragment() {

    lateinit var binding: FragmentPwFindingThirdBinding
    var pw: String = ""
    var isValidPW: Boolean = false
    var pwAgain: String = "1"
    var email: String = ""
    private val requestData = RetrofitBuilder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pw_finding_third, container, false)

        binding = FragmentPwFindingThirdBinding.bind(view)

        val bundle = (activity as PwFindingActivity).mBundle
        email = bundle.getString("email").toString()

        getPassword()

        return binding.root
    }

    private fun getPassword() {
        binding.userPw.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                pw = binding.userPw.text.toString()
                binding.isUsablePw.visibility = View.VISIBLE
                Log.d("textchanged", pw)

                binding.isUsablePw.text = "사용하실 수 없는 비밀번호입니다."
                binding.isUsablePw.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_pink_sub
                    )
                )

                isValidPW = isValidPW(pw)
                Log.d("signup", isValidPW.toString())
                if (isValidPW) {
                    binding.isUsablePw.text = "사용가능한 비밀번호입니다."
                    binding.isUsablePw.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_green_main
                        )
                    )

                    checkPwAgain(pw)
                }
                checkPW()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun isValidPW(password: String): Boolean {
        Log.d("isvalid function", "들어옴")
        val reg =
            Regex("^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,}\$")
        if (!password.matches(reg)) {
            return false
        }
        return true
    }

    private fun checkPwAgain(first: String) {
        binding.userPwAgain.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                pwAgain = binding.userPwAgain.text.toString()
                checkPW()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
        binding.signUpButton.setBackgroundColor(
            ContextCompat.getColor(
                binding.root.context,
                R.color.cherish_light_gray
            )
        )
        binding.signUpButton.setTextColor(
            ContextCompat.getColor(
                binding.root.context,
                R.color.cherish_pass_text_gray
            )
        )
        Log.d("pwagain", pwAgain)
        if (first == pwAgain) {
            binding.isUsablePw.text = "비밀번호가 일치합니다."
            binding.isUsablePw.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.cherish_green_main
                )
            )
            //버튼 초록색 활성화
            binding.signUpButton.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.cherish_green_main
                )
            )
            binding.signUpButton.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )

            binding.signUpButton.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("email", email)
                bundle.putString("password", pw)

                (activity as SignUpActivity).postData(bundle)
                (activity as SignUpActivity).replaceFragment(1)
            }

        }
        if (pwAgain != "1") {
            binding.isUsablePw.text = "비밀번호가 일치하지 않습니다."
            binding.isUsablePw.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.cherish_pink_sub
                )
            )
        }
    }

    private fun checkPW() {
        if (pw == pwAgain) {
            binding.isUsablePw.text = "비밀번호가 일치합니다."
            binding.isUsablePw.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.cherish_green_main
                )
            )

            //버튼 초록색 활성화
            binding.signUpButton.setBackgroundColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.cherish_green_main
                )
            )
            binding.signUpButton.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.white
                )
            )

            binding.signUpButton.setOnClickListener {
                requestServer(email, pw, pwAgain)
            }
        }
    }

    private fun requestServer(email: String, password1: String, password2: String) {
        requestData.updatePasswordAPI.postPwFUpdate(
            RequestUpdatePasswordAPI(email = email, password1 = password1, password2 = password2)
        ).enqueue(
            object : Callback<ResponseUpdatePasswordAPI> {
                override fun onFailure(call: Call<ResponseUpdatePasswordAPI>, t: Throwable) {
                    Log.d("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponseUpdatePasswordAPI>,
                    response: Response<ResponseUpdatePasswordAPI>
                ) {
                    response.takeIf {
                        it.isSuccessful
                    }?.body()
                        ?.let { it ->
                            if (it.success) {
                                val intent = Intent(context, SignInActivity::class.java)
                                startActivity(intent)
                                requireActivity().finish()
                            }
                        }
                }
            }
        )
    }
}