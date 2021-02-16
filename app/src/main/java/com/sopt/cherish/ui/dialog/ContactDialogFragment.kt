package com.sopt.cherish.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
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
import com.sopt.cherish.ui.review.ReviewActivity
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.extension.shortToast

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
 * todo : Calendar CherishId 7번이 이상함? date 타입이 다른거 같은데
 */

class ContactDialogFragment(private val cherishId: Int) : DialogFragment(), View.OnClickListener {
    private val codeThatReviewPage = 1001
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var binding: DialogContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_contact, container, false)
        viewModel.fetchCalendarData()
        binding.mainViewModel = viewModel


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeChip(binding)
        setChip(binding)
    }

    private fun initializeChip(binding: DialogContactBinding) {
        viewModel.calendarData.observe(viewLifecycleOwner) {
            if (it.waterData.calendarData.isNullOrEmpty()) {
                // todo : 이거 어떻게 고쳐야 할까?
                binding.contactUserStatusFirstChip.text = "채워줘ㅠ"
                binding.contactUserStatusSecondChip.text = "채워줘ㅠ"
                binding.contactUserStatusThridChip.text = "채워줘ㅠ"
            } else {
                // todo : 이거 viewModel에서 list로 변경해서 할거임
                viewModel.userStatus1.value = it.waterData.calendarData[0].userStatus1
                viewModel.userStatus2.value = it.waterData.calendarData[0].userStatus2
                viewModel.userStatus3.value = it.waterData.calendarData[0].userStatus3
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
        viewModel.userStatus1.observe(viewLifecycleOwner) { chipText ->
            if (chipText == null) {
                binding.contactUserStatusFirstChip.text = " "
            }
            binding.contactUserStatusFirstChip.text = chipText
        }
        viewModel.userStatus2.observe(viewLifecycleOwner) { chipText ->
            if (chipText == null) {
                binding.contactUserStatusSecondChip.text = " "
            }
            binding.contactUserStatusSecondChip.text = chipText
        }
        viewModel.userStatus3.observe(viewLifecycleOwner) { chipText ->
            if (chipText == null) {
                binding.contactUserStatusThridChip.text = " "
            }
            binding.contactUserStatusThridChip.text = chipText
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
            Intent(
                Intent.ACTION_CALL,
                Uri.parse("tel:${viewModel.selectedCherishUser.value!!.phoneNumber}")
            )
        startActivityForResult(
            callIntent,
            codeThatReviewPage
        )
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
            startReviewAndDismiss(cherishId)
        }
        if (resultCode == RESULT_OK) {
            viewModel.animationTrigger.value = data?.getBooleanExtra("reviewCode", true)
        }
    }


    private fun startReviewAndDismiss(cherishId: Int) {
        val intent = Intent(requireContext(), ReviewActivity::class.java)
        intent.putExtra("userNickname", viewModel.userNickName.value)
        intent.putExtra("selectedCherishNickname", viewModel.selectedCherishUser.value!!.nickName)
        intent.putExtra("selectedCherishId", viewModel.selectedCherishUser.value!!.id)
        startActivity(intent)
        dismiss()
    }
}