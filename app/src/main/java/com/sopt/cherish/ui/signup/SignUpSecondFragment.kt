package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpSecondBinding
import com.sopt.cherish.remote.api.RequestPhoneAuthData
import com.sopt.cherish.remote.api.ResponsePhoneAuthData
import com.sopt.cherish.remote.singleton.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpSecondFragment : Fragment() {
    lateinit var binding:FragmentSignUpSecondBinding
    lateinit var phoneNumber:String
    private val requestData = RetrofitBuilder
    var authData:String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_second, container, false)

        binding= FragmentSignUpSecondBinding.bind(view)
        binding.userPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        getCertificationNumber()

        return view
    }

    private fun getCertificationNumber(){
        binding.certificationBtn.setOnClickListener {
            phoneNumber=binding.userPhone.text.toString()
            Log.d("phoneNumber",phoneNumber)
            requestServer(phoneNumber)

            binding.certificationBtn.visibility=View.GONE
            binding.certificationText.visibility=View.VISIBLE
            binding.userCertificationNumber.visibility=View.VISIBLE
            binding.certificationAgain.visibility=View.VISIBLE
            binding.certificationOk.visibility=View.VISIBLE

            checkCertificationNumber(authData)
        }

        binding.certificationAgain.setOnClickListener{
            requestServer(phoneNumber)
        }
    }

    private fun requestServer(phoneNumber:String){
        requestData.phoneAuthAPI.postAuth(
            RequestPhoneAuthData(phone=phoneNumber)
        ).enqueue(
            object: Callback<ResponsePhoneAuthData> {
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
                        ?.let{  it->
                            Log.d("phone success",it.success.toString())
                            Log.d("phone message",it.message)
                            Log.d("phone data",it.data.toString())

                            authData=it.data.toString()
                        }
                }
            }
        )
    }

    private fun checkCertificationNumber(authData:String){
        if(authData==binding.userCertificationNumber.text.toString()){ //인증번호 일치하면
            binding.certificationOk.text="인증번호가 일치합니다."
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
                (activity as SignUpActivity).replaceFragment(2)
            }
        }
    }

}