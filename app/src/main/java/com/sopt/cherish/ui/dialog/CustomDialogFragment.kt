package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.SampleLottie2Binding
import com.sopt.cherish.databinding.SampleLottieBinding
import com.sopt.cherish.util.AdjustDialog

/**
 * Created on 2021-1-1 by SSong-develop
 * dialog에 비즈니스 로직이 들어가지는 않는 간단한 dialog는 이 클래스를 사용해 제어
 */

class CustomDialogFragment(
        @LayoutRes private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return when (layoutResId) {
            R.layout.sample_lottie -> {
                val binding = SampleLottieBinding.bind(view)

                binding.root
            }
            R.layout.sample_lottie2 -> {
                val binding = SampleLottie2Binding.bind(view)
                binding.root
            }
            else -> {
                view
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val adjustDialogSize = AdjustDialog(requireContext())

        adjustDialogSize.adjustSize(dialogFragment = this, widthRatio = 0.9f, heightRatio = 0.45f)
    }

    override fun onClick(view: View?) {
        dismiss()
    }
}