package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpThirdBinding

class SignUpThirdFragment : Fragment() {
    lateinit var binding: FragmentSignUpThirdBinding
    private val genders = arrayOf("여성", "남성")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up_third, container, false)

        binding = FragmentSignUpThirdBinding.bind(view)

        initializePicker()

        return view
    }

    private fun initializePicker() {
        binding.userSex.minValue = 0
        binding.userSex.maxValue = 1
        binding.userSex.displayedValues = genders
        binding.userSex.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        binding.userSex.wrapSelectorWheel = true

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
            (activity as SignUpActivity).replaceFragment(3)
        }
    }

}