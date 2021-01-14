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
import com.sopt.cherish.ui.review.ReviewActivity
import com.sopt.cherish.util.DialogUtil
import com.sopt.cherish.util.PermissionUtil
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.shortToast

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
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
        binding.mainViewModel = viewModel

        // 유저 닉네임과 상태 chip들을 서버에서 받은 값으로 전해서 표현해야한다.
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

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.875f, 0.542f)
    }

    override fun onClick(view: View?) {
        dismiss()
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
            viewModel.userNickName.observe(viewLifecycleOwner) {
                kakaoIntent?.putExtra("userNickname", it)
            }
            viewModel.cherishUser.observe(viewLifecycleOwner) {
                kakaoIntent?.putExtra("cherishNickname", it.nickName)
            }
            startActivityForResult(kakaoIntent, codeThatReviewPage)
        } else {
            shortToast(requireContext(), "카카오톡이 없어요 ㅠ")
        }
    }

    private fun startPhoneCall() {
        // 함수화 해야합니다.
        val callIntent =
            Intent(Intent.ACTION_CALL, Uri.parse("tel:${viewModel.cherishUser.value?.phoneNumber}"))
        callIntent.putExtra("userNickname", viewModel.userNickName.value)
        callIntent.putExtra("cherishNickname", viewModel.cherishUser.value?.nickName)
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
        viewModel.userNickName.observe(viewLifecycleOwner) {
            SimpleLogger.logI(it)
            messageIntent.putExtra("userNickname", it)
        }
        viewModel.cherishUser.observe(viewLifecycleOwner) {
            messageIntent.putExtra("cherishNickname", it.nickName)
        }

        startActivityForResult(
            messageIntent,
            codeThatReviewPage
        )
    }

    private fun startReviewAndDismiss() {
        startActivity(Intent(requireContext(), ReviewActivity::class.java))
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