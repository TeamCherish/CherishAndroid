package com.sopt.cherish.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import com.sopt.cherish.R
import com.sopt.cherish.databinding.BirthpickerLayoutBinding

class BirthPickerDialogFragment(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    var birthtext = ""

    interface TestDialogFragmentListener {
        fun onTestDialogBirth(dialog: DialogFragment?, someData: String?)
    }

    var testDialogFragmentListener: TestDialogFragmentListener? = null

    fun someAction() {
        testDialogFragmentListener!!.onTestDialogBirth(
            this@BirthPickerDialogFragment, birthtext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        testDialogFragmentListener =
            activity as BirthPickerDialogFragment.TestDialogFragmentListener

        val binding = BirthpickerLayoutBinding.bind(view)

        val birthMonth: NumberPicker = view.findViewById(R.id.numberPicker)
        val birthDay: NumberPicker = view.findViewById(R.id.numberPicker3)

        //val birth=resources.getStringArray(R.array.day)
        val cancel: Button = view.findViewById(R.id.button_alarm)
        cancel.setOnClickListener {
            dismiss()

        }
        birthMonth.minValue = 1
        birthMonth.maxValue = 12

        birthDay.minValue = 1
        birthDay.maxValue = 31


        //birthMonth.displayedValues = list_every
        // birthDay.displayedValues = list_Day

        birthMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        birthDay.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        val btn_ok: Button = view.findViewById(R.id.button_ok)
        btn_ok.setOnClickListener {

            birthtext =
                birthMonth.value.toString() + "/" + birthDay.value
            someAction()
            dialog?.dismiss()

        }
        return binding.root
    }
}