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
import com.sopt.cherish.databinding.FragmentModifyWeekAlertBinding


class ModifyWeekAlertFragment(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment() {

    private lateinit var binding: FragmentModifyWeekAlertBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        binding = FragmentModifyWeekAlertBinding.bind(view)

        binding.buttonClose.setOnClickListener {
            dismiss()
            val needweek = WeekPickerDialogFragment(R.layout.weekpicker_layout).show(
                parentFragmentManager,
                "MainActivity"
            )
        }


        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))



        return binding.root
    }


}