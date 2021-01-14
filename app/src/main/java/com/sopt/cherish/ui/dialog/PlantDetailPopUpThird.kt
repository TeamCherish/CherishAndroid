package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.DialogFragment
import com.google.android.material.behavior.SwipeDismissBehavior
import com.sopt.cherish.R
import com.sopt.cherish.databinding.FragmentPlantDetailPopUpThirdBinding
import com.sopt.cherish.ui.datail.DetailPlantActivity

class PlantDetailPopUpThird : DialogFragment() {


    private lateinit var binding: FragmentPlantDetailPopUpThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_plant_detail_pop_up_third, container, false)

        binding = FragmentPlantDetailPopUpThirdBinding.bind(view)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dismiss: CardView = binding.dialogFirst
        val params = dismiss.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = SwipeDismissBehavior<View>()
        behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)

        behavior.listener = object : SwipeDismissBehavior.OnDismissListener {
            override fun onDismiss(view: View) {
                dialog!!.dismiss()
            }

            override fun onDragStateChanged(i: Int) {
                (activity as DetailPlantActivity).setFragment(PlantDetailPopUpThird())
            }
        }

        params.behavior = behavior

        return binding.root
    }

}