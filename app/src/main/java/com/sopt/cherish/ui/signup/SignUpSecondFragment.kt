package com.sopt.cherish.ui.signup

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
import com.sopt.cherish.databinding.FragmentSignUpSecondBinding
import com.sopt.cherish.remote.api.RequestPhoneAuthData
import com.sopt.cherish.remote.api.ResponsePhoneAuthData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class SignUpSecondFragment : Fragment() {
    lateinit var binding: FragmentSignUpSecondBinding
    lateinit var phoneNumber: String
    private val requestData = RetrofitBuilder
    var authData: String = ""
    var email: String = ""
    var password: String = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_second, container, false)

        binding = FragmentSignUpSecondBinding.bind(view)
        //binding.userPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())


        val bundle = (activity as SignUpActivity).mBundle
        email = bundle.getString("email").toString()
        password = bundle.getString("password").toString()

        getCertificationNumber()

        return view
    }

    private fun getCertificationNumber() {
        binding.userPhone.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                phoneNumber = binding.userPhone.text.toString()

                //휴대폰번호 유효성 검사
                if (isPhoneNumberValid(phoneNumber)) {
                    binding.certificationBtn.setOnClickListener {
                        Log.d("phoneNumber", phoneNumber)
                        requestServer(phoneNumber)

                        binding.certificationBtn.visibility = View.GONE
                        binding.certificationText.visibility = View.VISIBLE
                        binding.userCertificationNumber.visibility = View.VISIBLE
                        binding.certificationAgain.visibility = View.VISIBLE
                        binding.certificationOk.visibility = View.VISIBLE

                        checkCertificationNumber()
                    }

                    binding.certificationAgain.setOnClickListener {
                        requestServer(phoneNumber)
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

    }

    private fun isPhoneNumberValid(phone: String): Boolean {
        if (!Pattern.matches(
                "^\\s*(010|011|016|017|018|019)(|\\)|\\s)*(\\d{3,4})(|\\s)*(\\d{4})\\s*$",
                phone
            )
        ) {
            return false
        }
        return true
    }

    private fun requestServer(phoneNumber: String) {
        requestData.phoneAuthAPI.postAuth(
            RequestPhoneAuthData(phone = phoneNumber)
        ).enqueue(
            object : Callback<ResponsePhoneAuthData> {
                override fun onFailure(call: Call<ResponsePhoneAuthData>, t: Throwable) {
                    Log.d("통신 실패", t.toString())
                }

                override fun onResponse(
                    call: Call<ResponsePhoneAuthData>,
                    response: Response<ResponsePhoneAuthData>
                ) {
                    response.takeIf {
                        it.isSuccessful
                    }?.body()
                        ?.let { it ->
                            authData = it.data.toString()
                        }
                }
            }
        )
    }

    private fun checkCertificationNumber() {

        binding.userCertificationNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val certificationNumber = binding.userCertificationNumber.text.toString()

                Log.d("auth", certificationNumber)
                Log.d("authData", authData)
                if (authData == certificationNumber) {
                    binding.certificationOk.visibility = View.VISIBLE
                    binding.certificationOk.text = "인증번호가 일치합니다."
                    binding.certificationOk.setTextColor(
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
                        val myBundle = Bundle()
                        myBundle.putString("email", email)
                        myBundle.putString("password", password)
                        myBundle.putString("phone", phoneNumber)

                        (activity as SignUpActivity).postData(myBundle)
                        (activity as SignUpActivity).replaceFragment(2)
                    }
                } else {
                    //버튼 비활성화
                    binding.signUpButton.setBackgroundColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_text_box_gray
                        )
                    )
                    binding.signUpButton.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.cherish_text_gray
                        )
                    )
                    binding.certificationOk.visibility = View.INVISIBLE
                }
            }
        })
    }

}