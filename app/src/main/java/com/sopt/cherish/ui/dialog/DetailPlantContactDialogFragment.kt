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
import com.sopt.cherish.databinding.DialogDetailPlantContactBinding
import com.sopt.cherish.ui.detail.DetailPlantViewModel
import com.sopt.cherish.ui.review.DetailPlantDialogReviewFragment
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.shortToast

class DetailPlantContactDialogFragment(private val cherishId: Int) : DialogFragment(),
    View.OnClickListener {
    private val codeThatReviewPage = 1002
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
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.detailPlantViewModel = viewModel
        viewModel.cherishId.value = cherishId
        viewModel.fetchCalendarData()
        viewModel.calendarData.observe(viewLifecycleOwner) {
            if (it.waterData.calendarData.isEmpty()) {
                binding.detailPlantContactUserStatusFirstChip.text = "채워줘ㅠ"
                binding.detailPlantContactUserStatusSecondChip.text = "채워줘ㅠ"
                binding.detailPlantContactUserStatusThridChip.text = "채워줘ㅠ"
            } else {
                viewModel.userStatus1.value = it.waterData.calendarData[0].userStatus1
                viewModel.userStatus2.value = it.waterData.calendarData[0].userStatus2
                viewModel.userStatus3.value = it.waterData.calendarData[0].userStatus3
                setChip(binding)
            }
        }

        binding.detailPlantContactCall.setOnClickListener {
            navigateCall()
        }

        binding.detailPlantContactKakaoTalk.setOnClickListener {
            navigateKakao()
        }

        binding.detailPlantContactMessage.setOnClickListener {
            navigateToSendMessage()
        }


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
        viewModel.userStatus1.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.detailPlantContactUserStatusFirstChip.text = " "
            }
            binding.detailPlantContactUserStatusFirstChip.text = it
        }
        viewModel.userStatus2.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.detailPlantContactUserStatusSecondChip.text = " "
            }
            binding.detailPlantContactUserStatusSecondChip.text = it
        }
        viewModel.userStatus3.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.detailPlantContactUserStatusThridChip.text = " "
            }
            binding.detailPlantContactUserStatusThridChip.text = it
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

    private fun startReviewAndDismiss() {
        DetailPlantDialogReviewFragment().show(parentFragmentManager, tag)
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeThatReviewPage) {
            startReviewAndDismiss()
        }
    }
}