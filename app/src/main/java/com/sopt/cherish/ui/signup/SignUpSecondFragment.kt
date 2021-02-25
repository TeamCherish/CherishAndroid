package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpFirstBinding
import com.sopt.cherish.databinding.FragmentSignUpSecondBinding


class SignUpSecondFragment : Fragment() {
    lateinit var binding:FragmentSignUpSecondBinding

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

        }
    }

}