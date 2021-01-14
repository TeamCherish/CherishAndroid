package com.sopt.cherish.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.cherish.databinding.FragmentHomeBlankBinding
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity


class HomeBlankFragment : Fragment() {

    private lateinit var binding: FragmentHomeBlankBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBlankBinding.inflate(layoutInflater)

        binding.startBtn.setOnClickListener {
            val intent = Intent(context, EnrollmentPhoneActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}