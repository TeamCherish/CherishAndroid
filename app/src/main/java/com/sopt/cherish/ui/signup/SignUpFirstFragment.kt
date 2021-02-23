package com.sopt.cherish.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSignUpFirstBinding

class SignUpFirstFragment: Fragment() {

    lateinit var binding:FragmentSignUpFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up_first, container, false)

        binding=FragmentSignUpFirstBinding.bind(view)

        return view
    }
}