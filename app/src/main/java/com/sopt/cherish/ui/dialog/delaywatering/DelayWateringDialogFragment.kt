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
import com.sopt.cherish.databinding.DialogDelayWateringBinding
import com.sopt.cherish.remote.api.PostponeWateringDateReq
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.extension.shortToast

class DelayWateringDialogFragment : DialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: DialogDelayWateringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_delay_watering, container, false)
        binding.mainViewModel = viewModel
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        initializeNumberPicker(binding)
        sendDelayDayToServer(binding)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.9f, 0.6f)
    }

    private fun initializeNumberPicker(binding: DialogDelayWateringBinding) {
        binding.delayWateringDayPicker.apply {
            wrapSelectorWheel = false
            maxValue = 7
            minValue = 1
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
    }

    // todo : 갱신이 안돼 훈기야
    private fun sendDelayDayToServer(binding: DialogDelayWateringBinding) {
        // 함수화 해야합니다 진짜.
        binding.delayWateringAcceptBtn.setOnClickListener {
            viewModel.postponeWateringDate(
                PostponeWateringDateReq(
                    viewModel.selectedCherishUser.value!!.id,
                    binding.delayWateringDayPicker.value,
                    true
                )
            )
            viewModel.isWatered.value = false
            shortToast(requireContext(), "미루기 성공!")
            viewModel.fetchUsers()
            dismiss()
        }
    }
}