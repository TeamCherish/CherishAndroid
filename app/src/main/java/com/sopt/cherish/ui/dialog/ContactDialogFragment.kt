package com.sopt.cherish.ui.dialog

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
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
import com.sopt.cherish.ui.main.MainViewModel
import com.sopt.cherish.ui.review.DialogReviewFragment
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.shortToast

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
 * todo : Calendar CherishId 7번이 이상함? date 타입이 다른거 같은데
 */

class ContactDialogFragment : DialogFragment(), View.OnClickListener {
    private val codeThatReviewPage = 1001
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogContactBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_contact, container, false)
        viewModel.fetchCalendarData()
        binding.mainViewModel = viewModel
        initializeChip(binding)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.contactCall.setOnClickListener {
            navigateCall()
        }

        binding.contactKakaoTalk.setOnClickListener {
            navigateKakao()
        }

        binding.contactMessage.setOnClickListener {
            navigateToSendMessage()
        }

        return binding.root
    }

    private fun initializeChip(binding: DialogContactBinding) {
        viewModel.calendarData.observe(viewLifecycleOwner) {
            if (it.waterData.calendarData.isEmpty()) {
                binding.contactUserStatusFirstChip.text = "채워줘ㅠ"
                binding.contactUserStatusSecondChip.text = "채워줘ㅠ"
                binding.contactUserStatusThridChip.text = "채워줘ㅠ"
            } else {
                viewModel.userStatus1.value = it.waterData.calendarData[0].userStatus1
                viewModel.userStatus2.value = it.waterData.calendarData[0].userStatus2
                viewModel.userStatus3.value = it.waterData.calendarData[0].userStatus3
                setChip(binding)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.542f)
    }

    override fun onClick(view: View?) {
        dismiss()
    }

    private fun setChip(binding: DialogContactBinding) {
        viewModel.userStatus1.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.contactUserStatusFirstChip.text = " "
            }
            binding.contactUserStatusFirstChip.text = it
        }
        viewModel.userStatus2.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.contactUserStatusSecondChip.text = " "
            }
            binding.contactUserStatusSecondChip.text = it
        }
        viewModel.userStatus3.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.contactUserStatusThridChip.text = " "
            }
            binding.contactUserStatusThridChip.text = it
        }
    }

    private fun navigateCall() {
        if (PermissionUtil.isCheckedCallPermission(requireContext())) {
            startPhoneCall()
        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }
    }

    private fun navigateToSendMessage() {
        if (PermissionUtil.isCheckedSendMessagePermission(requireContext())) {
            startSendMessage()
        } else {
            PermissionUtil.openPermissionSettings(requireContext())
        }
    }

    private fun navigateKakao() {
        if (findKakaoTalk()) {
            val kakaoIntent = context?.packageManager?.getLaunchIntentForPackage("com.kakao.talk")
            kakaoIntent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivityForResult(kakaoIntent, codeThatReviewPage)
        } else {
            shortToast(requireContext(), "카카오톡이 없어요 ㅠ")
        }
    }

    private fun startPhoneCall() {
        // 함수화 해야합니다.
        val callIntent =
            Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.cherishUser.value?.phoneNumber}"))
        startActivityForResult(
            callIntent,
            codeThatReviewPage
        )
    }

    private fun startSendMessage() {
        val messageIntent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("smsto:${viewModel.cherishUser.value?.phoneNumber}")
        )
        startActivityForResult(
            messageIntent,
            codeThatReviewPage
        )
    }

    private fun startReviewAndDismiss() {
        DialogReviewFragment().show(parentFragmentManager, tag)
        dismiss()
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun findKakaoTalk(): Boolean {
        var isExist = false
        val phoneApps: List<ResolveInfo>
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        phoneApps = context?.packageManager!!.queryIntentActivities(intent, 0)

        try {
            for (i in 0..phoneApps.size) {
                if (phoneApps[i].activityInfo.packageName.startsWith("com.kakao.talk")) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }
        return isExist
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeThatReviewPage) {
            startReviewAndDismiss()
        }
    }
}