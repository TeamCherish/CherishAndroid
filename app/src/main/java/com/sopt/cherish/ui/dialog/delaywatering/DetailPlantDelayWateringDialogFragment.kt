package com.sopt.cherish.ui.dialog.delaywatering

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogDetailDelayWateringBinding
import com.sopt.cherish.ui.detail.DetailPlantViewModel

class DetailPlantDelayWateringDialogFragment : DialogFragment() {

    private val viewModel: DetailPlantViewModel by activityViewModels()
    private lateinit var binding: DialogDetailDelayWateringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.dialog_detail_delay_watering,
                container,
                false
            )
        binding.detailPlantViewModel = viewModel
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initializeNumberPicker(binding)
        return binding.root

    }

    private fun initializeNumberPicker(binding: DialogDetailDelayWateringBinding) {
        binding.detailDelayWateringDayPicker.apply {
            wrapSelectorWheel = false
            maxValue = 7
            minValue = 1
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
    }
}