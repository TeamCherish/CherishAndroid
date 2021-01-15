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
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpThirdBinding

class PlantDetailPopUpThird : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentPlantDetailPopUpThirdBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_plant_detail_pop_up_third,
                container,
                false
            )

        return binding.root
    }

}