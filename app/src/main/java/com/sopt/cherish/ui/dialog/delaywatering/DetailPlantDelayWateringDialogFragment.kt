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
import com.sopt.cherish.remote.api.PostponeWateringDateReq
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.extension.shortToast

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
        sendDelayDayToServer(binding)
        return binding.root

    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.9f, 0.6f)
    }
    
    private fun initializeNumberPicker(binding: DialogDetailDelayWateringBinding) {
        binding.detailDelayWateringDayPicker.apply {
            wrapSelectorWheel = false
            maxValue = 7
            minValue = 1
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
    }

    private fun sendDelayDayToServer(binding: DialogDetailDelayWateringBinding) {
        // 함수화 해야합니다 진짜.
        binding.detailDelayWateringAcceptBtn.setOnClickListener {
            viewModel.postponeWateringDate(
                PostponeWateringDateReq(
                    viewModel.cherishId.value!!,
                    binding.detailDelayWateringDayPicker.value,
                    true
                )
            )
            viewModel.animationTrigger.value = false
            shortToast(requireContext(), "미루기 성공!")
            dismiss()
        }
    }
}