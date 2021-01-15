package com.sopt.cherish.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.databinding.FragmentAlertPlantDialogBinding
import com.sopt.cherish.ui.adapter.DialogViewPagerAdapter


class AlertPlantDialogFragment : DialogFragment(), View.OnClickListener {

    private lateinit var viewpagerAdapter: DialogViewPagerAdapter
    private var _binding: FragmentAlertPlantDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlertPlantDialogBinding.inflate(inflater, container, false)

        viewpagerAdapter = DialogViewPagerAdapter(childFragmentManager)
        viewpagerAdapter.fragments = listOf(
            PlantDetailPopUpFirst(),
            PlantDetailPopUpSecond(),
            PlantDetailPopUpThird(),
            PlantDetailPopUpFourth()
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