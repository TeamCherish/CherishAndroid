package com.sopt.cherish.ui.dialog

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
        // ViewModel 사용 x

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
            val intent = context?.packageManager?.getLaunchIntentForPackage("com.kakao.talk")
            intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } else {
            // 카카오톡을 깔게 할지 or 그냥 카카오톡이 없다고 할지?
            /*val kakaoTalkPackageUri = "com.kakao.talk"
            val url = "market://details?id=$kakaoTalkPackageUri"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)*/
            shortToast(requireContext(), "카카오톡이 없어요 ㅠ")
        }
    }

    private fun startPhoneCall() {
        startActivityForResult(
            Intent(Intent.ACTION_CALL, viewModel.dummyUserPhoneNumber),
            codeThatReviewPage
        )
    }

    private fun startSendMessage() {
        startActivityForResult(
            Intent(Intent.ACTION_SENDTO, viewModel.dummyUserMessageNumber),
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