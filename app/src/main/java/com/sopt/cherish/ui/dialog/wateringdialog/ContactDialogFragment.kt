package com.sopt.cherish.ui.dialog.wateringdialog

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
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
import com.sopt.cherish.databinding.DialogContactBinding
import com.sopt.cherish.remote.api.NotificationRemindReviewReq
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.review.ReviewFragment
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.ContextExtension.isInstalledApp
import com.sopt.cherish.util.extension.ContextExtension.moveMarket
import com.sopt.cherish.util.extension.FlexBoxExtension.addBlackChipModeChoice
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
 * 끝! 진짜 끝! 멘트만 수정하면 됨!!!
 */

class ContactDialogFragment : DialogFragment(), View.OnClickListener {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogContactBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_contact, container, false)
        viewModel.fetchCalendarData()
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = viewModel
        binding.dialogContact = this

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setChip(binding)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.542f)
    }

    override fun onClick(view: View?) {
        dismiss()
    }

    private fun setChip(binding: DialogContactBinding) {
        viewModel.calendarData.observe(viewLifecycleOwner) {
            binding.contactChipLayout.apply {
                clearChips()
                if (it?.waterData?.calendarData?.isNotEmpty()!!) {
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
        if (PermissionUtil.isCheckedCallPermission(requireContext())) {
            startPhoneCall()
        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }
    }

    fun navigateToSendMessage() {
        if (PermissionUtil.isCheckedSendMessagePermission(requireContext())) {
            startSendMessage()
        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }
    }

    fun navigateKakao() {
        if (requireContext().isInstalledApp(KAKAO_PACKAGE_NAME)) {
            val kakaoIntent = requireContext().packageManager.getLaunchIntentForPackage(
                KAKAO_PACKAGE_NAME
            )
            kakaoIntent!!.flags = FLAG_ACTIVITY_REORDER_TO_FRONT
            startReview()
            startActivity(kakaoIntent)
        } else {
            requireContext().moveMarket(KAKAO_PACKAGE_NAME)
        }
        sendRemindReviewNotification()
    }

    private fun startPhoneCall() {
        val callIntent =
            Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:${viewModel.selectedCherishUser.value!!.phoneNumber}")
            )
        startActivityForResult(
            callIntent,
            codeThatReviewPage
        )
        sendRemindReviewNotification()
    }

    private fun startSendMessage() {
        val messageIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("smsto:${viewModel.selectedCherishUser.value!!.phoneNumber}")
        )
        startActivityForResult(
            messageIntent,
            codeThatReviewPage
        )
        sendRemindReviewNotification()
    }

    private fun startReview() {
/*        val intent = Intent(requireContext(), ReviewActivity::class.java)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("selectedCherishNickname", viewModel.selectedCherishUser.value!!.nickName)
        intent.putExtra("selectedCherishId", viewModel.selectedCherishUser.value!!.id)
        startActivityForResult(intent, codeThatGetWatering)*/

        parentFragmentManager.beginTransaction()
            .replace(R.id.home_parent_fragment_container, ReviewFragment()).commit()
        parentFragmentManager.executePendingTransactions()
        dismiss()
    }

    private fun sendRemindReviewNotification() {
        viewModel.sendRemindReview(NotificationRemindReviewReq(viewModel.selectedCherishUser.value!!.id))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeThatReviewPage) {
            if (resultCode == RESULT_CANCELED) {
                startReview()
            }
        }
        if (requestCode == codeThatGetWatering) {
            if (resultCode == RESULT_OK) {
                viewModel.isWatered.value = data?.getBooleanExtra("wateringTrigger", false)
                dismiss()
            }
        }
    }

    companion object {
        private const val KAKAO_PACKAGE_NAME = "com.kakao.talk"
        private const val codeThatReviewPage = 1001
        private const val codeThatGetWatering = 1002
    }
}