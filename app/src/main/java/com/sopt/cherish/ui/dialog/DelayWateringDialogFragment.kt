package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogDelayWateringBinding
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.extension.shortToast

class DelayWateringDialogFragment : DialogFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_delay_watering, container, false)
        val binding = DialogDelayWateringBinding.bind(view)
        binding.mainViewModel = viewModel
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        viewModel.getPostPoneWateringCount()

        initializeNumberPicker(binding)
        sendDelayDayToServer(binding)
        serverDataSetting(binding)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.9f, 0.6f)
    }

    private fun initializeNumberPicker(binding: DialogDelayWateringBinding) {
        binding.delayWateringDayPicker.wrapSelectorWheel = false
        binding.delayWateringDayPicker.maxValue = 7
        binding.delayWateringDayPicker.minValue = 1
        binding.delayWateringDayPicker.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
    }

    private fun serverDataSetting(binding: DialogDelayWateringBinding) {
        viewModel.postponeData.observe(viewLifecycleOwner) {
            val postponeCount = it.postponeData.wateredDateAndPostponeCount.postponeCount
            val currentDelayWateringDescription = "(현재까지 미룬 횟수 ${postponeCount}회)"
            binding.currentDelayWateringDescriptionText.text = currentDelayWateringDescription
        }
    }

    private fun sendDelayDayToServer(binding: DialogDelayWateringBinding) {
        binding.delayWateringAcceptBtn.setOnClickListener {
            shortToast(requireContext(), "서버통신해야함")
        }
    }
}