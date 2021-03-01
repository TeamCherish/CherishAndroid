package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.sopt.cherish.R
import com.sopt.cherish.databinding.DialogLoadingBinding
import com.sopt.cherish.util.DialogUtil

/**
 * Created on 2021-1-1 by SSong-develop
 * dialog에 비즈니스 로직이 들어가지는 않는 간단한 dialog는 이 클래스를 사용해 제어
 */

class CustomDialogFragment(
    @LayoutRes private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return when (layoutResId) {
            R.layout.dialog_loading -> {
                // 등록 후에 보낼 loading 화면
                val binding = DialogLoadingBinding.bind(view)
                Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.cherish_loading)
                    .into(binding.dialogLoadingImage)
                binding.root
            }
            R.layout.dialog_warning_keyword_limit_error -> {
                val binding = DialogLoadingBinding.bind(view)
                DialogUtil.adjustDialogSize(this, 0.694f, 0.169f)
                binding.root
            }
            else -> {
                view
            }
        }
    }

    override fun onResume() {
        super.onResume()
        DialogUtil.adjustDialogSize(this, 0.35f, 0.15f)
    }

    override fun onClick(view: View?) {
        dismiss()
    }
}