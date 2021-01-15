package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.behavior.SwipeDismissBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpFourthBinding
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpSecondBinding
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpThirdBinding


class PlantDetailPopUpFourth : Fragment() {

    private var _binding: FragmentPlantDetailPopUpFourthBinding?=null
    private val binding get()=_binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentPlantDetailPopUpFourthBinding.inflate(inflater,container,false)


        return binding.root
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding=null
    }
}