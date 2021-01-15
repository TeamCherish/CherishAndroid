package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.databinding.FragmentAlertPlantDialogBinding
import com.sopt.cherish.ui.adapter.DialogViewPagerAdapter


class AlertPlantDialogFragment(cherishid :Int) : DialogFragment(), View.OnClickListener {

    private lateinit var viewpagerAdapter: DialogViewPagerAdapter
    private var _binding: FragmentAlertPlantDialogBinding? = null
    private val binding get() = _binding!!
    private var cherishId:Int=cherishid


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        //var cherishId:Int=cherishid
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        _binding = FragmentAlertPlantDialogBinding.inflate(inflater, container, false)

        Log.d("cherishId in fragment",cherishId.toString())
        viewpagerAdapter = DialogViewPagerAdapter(childFragmentManager)
        viewpagerAdapter.fragments = listOf(
            PlantDetailPopUpFirst(cherishId),
            PlantDetailPopUpSecond(cherishId),
            PlantDetailPopUpThird(cherishId),
            PlantDetailPopUpFourth(cherishId)
        )

        binding.dialogViewpager.adapter = viewpagerAdapter

        return binding.root

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        dismiss()
    }
}