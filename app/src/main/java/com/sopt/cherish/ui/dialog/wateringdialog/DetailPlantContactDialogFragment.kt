package com.sopt.cherish.ui.dialog.wateringdialog

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogDetailPlantContactBinding
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.review.ReviewActivity
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.extension.ContextExtension.isInstalledApp
import com.sopt.cherish.util.extension.ContextExtension.moveMarket
import com.sopt.cherish.util.extension.FlexBoxExtension.addBlackChipModeChoice
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips

class DetailPlantContactDialogFragment : DialogFragment(),
    View.OnClickListener {
    private val viewModel: DetailPlantViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogDetailPlantContactBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_detail_plant_contact,
            container,
            false
        )
        viewModel.fetchCalendarData()
        viewModel.wateringText = "${viewModel.cherishNickname}님과"
        binding.dialogDetailPlantContact = this
        binding.detailPlantViewModel = viewModel

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setChip(binding)
        return binding.root
    }

    override fun onClick(p0: View?) {
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.542f)
    }

    private fun setChip(binding: DialogDetailPlantContactBinding) {
        viewModel.calendarData.observe(viewLifecycleOwner) {
            binding.detailPlantContactChipLayout.apply {
                clearChips()
                if (it.waterData.calendarData.isNotEmpty()) {
                    it.waterData.calendarData.let { calendarData ->
                        if (calendarData.last().userStatus1 != "" && calendarData.last().userStatus1 != "null")
                            addBlackChipModeChoice(it.waterData.calendarData.last().userStatus1)
                        if (calendarData.last().userStatus2 != "" && calendarData.last().userStatus2 != "null")
                            addBlackChipModeChoice(it.waterData.calendarData.last().userStatus2)
                        if (calendarData.last().userStatus3 != "" && calendarData.last().userStatus3 != "null")
                            addBlackChipModeChoice(it.waterData.calendarData.last().userStatus3)
                    }
                }
            }
        }
    }

    fun navigateCall() {
/*
        if (PermissionUtil.isCheckedCallPermission(requireContext())) {

        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }
*/

        startPhoneCall()
    }

    fun navigateToSendMessage() {
/*        if (PermissionUtil.isCheckedSendMessagePermission(requireContext())) {

        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }*/

        startSendMessage()
    }

    fun navigateKakao() {
        if (requireContext().isInstalledApp(KAKAO_PACKAGE_NAME)) {
            val kakaoIntent = requireContext().packageManager.getLaunchIntentForPackage(
                KAKAO_PACKAGE_NAME
            )
            kakaoIntent?.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            startReview()
            startActivity(kakaoIntent)
        } else {
            requireContext().moveMarket(KAKAO_PACKAGE_NAME)
        }
    }

    private fun startPhoneCall() {
        val callIntent =
            Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.cherishPhoneNumber.value}"))
        startActivityForResult(
            callIntent,
            codeThatReviewPage
        )
    }

    private fun startSendMessage() {
        val messageIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("smsto:${viewModel.cherishPhoneNumber.value}")
        )
        startActivityForResult(
            messageIntent,
            codeThatReviewPage
        )
    }

    private fun startReview() {
        val intent = Intent(requireContext(), ReviewActivity::class.java)
        intent.putExtra("userNickname", viewModel.userNickname.value)
        intent.putExtra("myPageUserNickname", viewModel.myPageUserNickname.value)
        intent.putExtra("myPageUserId", viewModel.myPageUserId.value)
        intent.putExtra("selectedCherishNickname", viewModel.cherishNickname.value)
        intent.putExtra("selectedCherishId", viewModel.cherishId.value)
        startActivityForResult(intent, codeThatGetWatering)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeThatReviewPage) {
            startReview()
        }
        if (requestCode == codeThatGetWatering) {
            if (resultCode == RESULT_OK) {
                viewModel.animationTrigger.value = data?.getBooleanExtra("wateringTrigger", false)
                dismiss()
                val intent = Intent()
                intent.putExtra("animationTrigger", viewModel.animationTrigger.value)
                requireActivity().setResult(RESULT_OK, intent)
                requireActivity().finish()
            }
        }
    }

    companion object {
        private const val KAKAO_PACKAGE_NAME = "com.kakao.talk"
        private const val codeThatReviewPage = 1001
        private const val codeThatGetWatering = 1002
    }
}