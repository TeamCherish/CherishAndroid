package com.sopt.cherish.ui.signup


import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpThirdBinding
import com.sopt.cherish.ui.dialog.selectgender.SelectGenderDialogFragment

class SignUpThirdFragment : Fragment(){
    lateinit var binding: FragmentSignUpThirdBinding
    var email: String = ""
    var password: String = ""
    var phoneNumber: String = ""
    var sex: Int = 0
    var postSex: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_third, container, false)

        binding = FragmentSignUpThirdBinding.bind(view)

        binding.userAge.addTextChangedListener(PhoneNumberFormattingTextWatcher())

        val bundle = (activity as SignUpActivity).mBundle

        email = bundle.getString("email").toString()
        password = bundle.getString("password").toString()
        phoneNumber = bundle.getString("phone").toString()


        binding.userSex.setOnClickListener(View.OnClickListener {
            val fm=requireActivity().supportFragmentManager
            val dialogFragment=SelectGenderDialogFragment()
            dialogFragment.setTargetFragment(this@SignUpThirdFragment,101)
            dialogFragment.show(fm,"SignUpActivity")
        })
        getUserAge()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        sex=data!!.getIntExtra("gender",0)
        Log.d("getGender",sex.toString())

        when(sex){
            0->binding.userSex.text="여성"
            1->binding.userSex.text="남성"
        }
    }


    private fun getUserAge() {
        binding.userAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val birth = binding.userAge.text.toString()

                if (birth.length >= 4) {
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
                        Log.d("picker", sex.toString())

                        when (sex) {
                            0 -> postSex = false
                            1 -> postSex = true
                        }

                        val myBundle = Bundle()
                        myBundle.putString("email", email)
                        myBundle.putString("password", password)
                        myBundle.putString("phone", phoneNumber)
                        myBundle.putString("birth", birth)
                        myBundle.putBoolean("sex", postSex)

                        (activity as SignUpActivity).postData(myBundle)
                        (activity as SignUpActivity).replaceFragment(3)
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
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })
    }


}