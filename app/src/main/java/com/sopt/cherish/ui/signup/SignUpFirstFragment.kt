package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpFirstBinding
import com.sopt.cherish.remote.api.RequestSignUpEmailData
import com.sopt.cherish.remote.api.ResponseEmailData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpFirstFragment : Fragment() {

    lateinit var binding: FragmentSignUpFirstBinding
    lateinit var email: String
    lateinit var pw: String
    lateinit var pwAgain: String
    private val requestData = RetrofitBuilder
    var checkEmail: Boolean = false //이메일 형식확인
    var isValid: Boolean = false //이메일 중복확인
    var isFinish: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_first, container, false)

        binding = FragmentSignUpFirstBinding.bind(view)

        checkEmail()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val activity = activity
        if (activity != null) {
            (activity as SignUpActivity).setActionBarTitlesignup("회원가입")
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> activity?.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkEmail() {
        isFinish = false

        binding.userEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                email = binding.userEmail.text.toString()

                binding.isUsableEmail.setTextAppearance(R.style.SignUpTextAppearance)

                checkEmail = isEmailValid(email) //이메일 형식 확인

                if (checkEmail) { //이메일 형식 올바르면
                    Log.d("email", "is usable ok!")
                    checkSameEmail(email) //이메일 중복 확인

                    binding.isUsableEmail.text = "사용가능한 이메일입니다."
                    binding.isUsableEmail.setTextColor(
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

                    if (!isFinish) {
                        binding.signUpButton.setOnClickListener {

                            if (isValid) { //중복 없으면
                                //Log.d("email","색깔아 바뀌어라")
                                binding.isUsableEmail.text = "사용가능한 이메일입니다."
                                binding.isUsableEmail.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.cherish_green_main
                                    )
                                )

                                showPw()
                            } else { //중복 있으면
                                binding.isUsableEmail.text = "사용하실 수 없는 이메일입니다."
                                binding.isUsableEmail.setTextColor(
                                    ContextCompat.getColor(
                                        binding.root.context,
                                        R.color.cherish_pink_sub
                                    )
                                )
                            }
                        }
                    }


                } else { //이메일 형식 올바르지 않으면
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
                    Log.d("email", "false")
                    binding.isUsableEmail.text = "사용하실 수 없는 이메일입니다."
                    binding.isUsableEmail.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_pink_sub
                        )
                    )

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun isEmailValid(email: String): Boolean {
        isFinish = false

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }
        return true
    }

    private fun checkSameEmail(userEmail: String) {
        isFinish = false

        if (!isFinish) {
            requestData.signUpEmailAPI.postEmail(
                RequestSignUpEmailData(email = userEmail)
            ).enqueue(
                object : Callback<ResponseEmailData> {
                    override fun onFailure(call: Call<ResponseEmailData>, t: Throwable) {
                        Log.d("통신 실패", t.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseEmailData>,
                        response: Response<ResponseEmailData>
                    ) {
                        Log.d("error", "이메일 중복")
                        binding.isUsableEmail.text = "이미 존재하는 이메일입니다."
                        binding.isUsableEmail.setTextColor(
                            ContextCompat.getColor(
                                binding.root.context,
                                R.color.cherish_pink_sub
                            )
                        )
                        response.takeIf {
                            it.isSuccessful
                        }?.body()
                            ?.let { it ->
                                Log.d("email", it.success.toString())
                                Log.d("email message", it.message)

                                isValid = it.success
                                Log.d("isValid", isValid.toString())

                            }
                    }
                }
            )

        }

    }

    private fun showPw() {
        binding.pwText.visibility = View.VISIBLE
        binding.userPwLayout.visibility = View.VISIBLE
        binding.pwAgainLayout.visibility = View.VISIBLE

        binding.signUpButton.text = "다음"

        binding.userPw.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                isFinish = false
                pw = binding.userPw.text.toString()
                binding.isUsablePw.visibility = View.VISIBLE

                binding.isUsablePw.text = "사용하실 수 없는 비밀번호입니다."
                binding.isUsablePw.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.cherish_pink_sub
                    )
                )

                if (pw.length >= 8) {
                    binding.isUsablePw.text = "사용가능한 비밀번호입니다."
                    binding.isUsablePw.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_green_main
                        )
                    )

                    checkPwAgain(pw)

                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

    private fun checkPwAgain(first: String) {

        binding.userPwAgain.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                pwAgain = binding.userPwAgain.text.toString()

                if (first == pwAgain) {
                    binding.isUsablePw.text = "비밀번호가 일치합니다."
                    binding.isUsablePw.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_green_main
                        )
                    )
                    isFinish = true
                    binding.signUpButton.setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString("email", email)
                        bundle.putString("password", pw)

                        (activity as SignUpActivity).postData(bundle)
                        (activity as SignUpActivity).replaceFragment(1)
                    }

                } else {
                    binding.isUsablePw.text = "비밀번호가 일치하지 않습니다."
                    binding.isUsablePw.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_pink_sub
                        )
                    )
                    isFinish = false
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }

}