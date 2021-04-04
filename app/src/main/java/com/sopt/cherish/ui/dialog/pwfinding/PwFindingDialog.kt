package com.sopt.cherish.ui.dialog.pwfinding

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.databinding.FragmentPwFindingDialogBinding
import com.sopt.cherish.remote.singleton.RetrofitBuilder

class PwFindingDialog(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentPwFindingDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentPwFindingDialogBinding.bind(view)

        binding.buttonClose.setOnClickListener {
            dismiss()
        }

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }
}