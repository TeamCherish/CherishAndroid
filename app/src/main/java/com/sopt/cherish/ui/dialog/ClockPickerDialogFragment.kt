package com.sopt.cherish.ui.dialog

import android.content.res.Resources
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
import com.sopt.cherish.databinding.ClockpickerLayoutBinding

//created by nayoung : 알람시간 타임피커 팝업뷰 창
class ClockPickerDialogFragment(@LayoutRes
                                private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val binding = ClockpickerLayoutBinding.bind(view)
        val clock: NumberPicker = view.findViewById(R.id.numberPicker_clock)
        val clock1: NumberPicker = view.findViewById(R.id.numberPicker2_clock)
        val clock2: NumberPicker = view.findViewById(R.id.numberPicker3_clock)

        val cancel: Button = view.findViewById(R.id.button_cancel_clock)
        cancel.setOnClickListener {
            dismiss()

        }
        val list = resources.getStringArray(R.array.ampm)


        clock.removeDivider()
        clock1.removeDivider()
        clock2.removeDivider()

        clock.minValue = 1
        clock.maxValue = 12

        clock1.minValue = 0
        clock1.maxValue = 59

        clock2.minValue = 0
        clock2.maxValue = list.size - 1

        clock2.displayedValues = list

        clock.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clock2.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clock1.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        return binding.root


    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    private fun NumberPicker.removeDivider() {
        val pickerFields = NumberPicker::class.java.declaredFields
        for (pf in pickerFields) {
            if (pf.name == "mSelectionDivider") {
                pf.isAccessible = true
                try {
                    val colorDrawable = ColorDrawable(Color.TRANSPARENT)
                    pf[this] = colorDrawable
                } catch (e: java.lang.IllegalArgumentException) {

                } catch (e: Resources.NotFoundException) {

                } catch (e: IllegalAccessException) {

                }
                break
            }
        }
    }
}