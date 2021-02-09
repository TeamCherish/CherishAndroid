package com.sopt.cherish.ui.dialog

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
    // todo : 오늘 하루 만약에 버튼을 클릭했다면 , 그 다음에는 불가능 하도록 해야함
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_delay_watering, container, false)
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
        binding.delayWateringDayPicker.apply {
            wrapSelectorWheel = false
            maxValue = 7
            minValue = 1
            descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        }
    }

    private fun serverDataSetting(binding: DialogDelayWateringBinding) {
        viewModel.postponeData.observe(viewLifecycleOwner) {
            val postponeCount = it.postponeData.wateredDateAndPostponeCount.postponeCount
            val currentDelayWateringDescription = "(현재까지 미룬 횟수 ${postponeCount}회)"
            binding.currentDelayWateringDescriptionText.text = currentDelayWateringDescription
        }
    }

    private fun sendDelayDayToServer(binding: DialogDelayWateringBinding) {
        // 함수화 해야합니다 진짜.
        binding.delayWateringAcceptBtn.setOnClickListener {
            viewModel.postponeData.observe(viewLifecycleOwner) {
                if (it.postponeData.isPostpone) {
                    val postponeWateringDateReq = PostponeWateringDateReq(
                        viewModel.selectedCherishUser.value!!.id,
                        binding.delayWateringDayPicker.value,
                        it.postponeData.isPostpone
                    )
                    viewModel.postponeWateringDate(postponeWateringDateReq)
                    viewModel.animationTrigger.value = false
                    shortToast(requireContext(), "[미루기 성공]3회 초과하였습니다 , 식물 애정도가 차감되었습니다.")
                    dismiss()
                } else {
                    val postponeWateringDateReq = PostponeWateringDateReq(
                        viewModel.selectedCherishUser.value!!.id,
                        binding.delayWateringDayPicker.value,
                        it.postponeData.isPostpone
                    )
                    viewModel.postponeWateringDate(postponeWateringDateReq)
                    viewModel.animationTrigger.value = false
                    shortToast(requireContext(), "미루기 성공!")
                    dismiss()
                }
            }
        }
    }
}