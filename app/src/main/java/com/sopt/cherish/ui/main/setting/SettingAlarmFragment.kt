package com.sopt.cherish.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentSettingAlarmBinding
import com.sopt.cherish.ui.enrollment.EnrollmentPhoneActivity
import com.sopt.cherish.ui.main.MainActivity


class SettingAlarmFragment : Fragment() {

    private lateinit var binding: FragmentSettingAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_setting_alarm, container, false)
        binding = FragmentSettingAlarmBinding.bind(view)

        binding.imageViewBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()

        }



        return binding.root
    }


}