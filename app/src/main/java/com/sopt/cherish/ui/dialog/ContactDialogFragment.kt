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
import com.sopt.cherish.util.AdjustDialog

/**
 * Created on 2020-01-03 by SSong-develop
 * popUp_Contact
 */

class ContactDialogFragment : DialogFragment(), View.OnClickListener {
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: DialogContactBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_contact, container, false)
        binding.mainViewModel = viewModel

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.contactCall.setOnClickListener {
            navigateCall()
        }

        binding.contactKakaoTalk.setOnClickListener {
            navigateKakao()
        }

        binding.contactMessage.setOnClickListener {
            navigateMessage()
        }

        return binding.root
    }

    override fun onClick(view: View?) {
        dismiss()
    }

    override fun onResume() {
        super.onResume()
        AdjustDialog(requireContext()).adjustSize(this, 0.875f, 0.5452f)
    }

    private fun navigateMessage() {
        val messageIntent = Intent(Intent.ACTION_SENDTO, viewModel.dummyUserMessageNumber)
        startActivity(messageIntent)
        dismiss()
    }

    private fun navigateKakao() {
        // Kakao Talk 창으로 가야함
    }

    private fun navigateCall() {
        val phoneCallIntent = Intent(Intent.ACTION_CALL, viewModel.dummyUserPhoneNumber)
        startActivity(phoneCallIntent)
        dismiss()
    }
}