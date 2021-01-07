package com.sopt.cherish.ui.dialog

import android.content.Intent
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

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
 */

class ContactDialogFragment : DialogFragment(), View.OnClickListener {
    private val codeReviewPage = 1001
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: DialogContactBinding =
            DataBindingUtil.inflate(inflater, R.layout.dialog_contact, container, false)
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
        // Kakao Talk 창으로 가야함
    }

    private fun startPhoneCall() {
        startActivityForResult(
            Intent(Intent.ACTION_CALL, viewModel.dummyUserPhoneNumber),
            codeReviewPage
        )
    }

    private fun startSendMessage() {
        startActivityForResult(
            Intent(Intent.ACTION_SENDTO, viewModel.dummyUserMessageNumber),
            codeReviewPage
        )
    }

    private fun startReviewAndDismiss() {
        startActivity(Intent(requireContext(), ReviewActivity::class.java))
        dismiss()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == codeReviewPage) {
            startReviewAndDismiss()
        }
    }
}