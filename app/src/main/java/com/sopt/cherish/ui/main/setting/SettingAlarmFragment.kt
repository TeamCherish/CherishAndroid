package com.sopt.cherish.ui.main.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentAlarmSettingBinding
import com.sopt.cherish.databinding.FragmentSettingAlarmBinding


class SettingAlarmFragment : Fragment() {

    private lateinit var binding:FragmentSettingAlarmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_setting_alarm, container, false)
        binding= FragmentSettingAlarmBinding.bind(view)




        return binding.root
    }


}