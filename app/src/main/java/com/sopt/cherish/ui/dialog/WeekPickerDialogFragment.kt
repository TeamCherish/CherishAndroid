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
import com.sopt.cherish.databinding.WeekpickerLayoutBinding

//created by nayoung : 알람주기 설정 보여주는 팝업 창
class WeekPickerDialogFragment(@LayoutRes
                               private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val binding = WeekpickerLayoutBinding.bind(view)


        val ny: NumberPicker = view.findViewById(R.id.numberPicker)
        val ny1: NumberPicker = view.findViewById(R.id.numberPicker2)
        val ny2: NumberPicker = view.findViewById(R.id.numberPicker3)

        val cancel: Button = view.findViewById(R.id.button_alarm)
        cancel.setOnClickListener {
            dismiss()

        }
        val list = resources.getStringArray(R.array.cycle)
        val list2 = resources.getStringArray(R.array.week)

        ny.removeDivider()
        ny1.removeDivider()
        ny2.removeDivider()

        ny.minValue = 0
        ny.maxValue = list2.size - 1

        ny1.minValue = 1
        ny1.maxValue = 3

        ny2.minValue = 0
        ny2.maxValue = list.size - 1

        ny.displayedValues = list2
        ny2.displayedValues = list

        ny.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        ny2.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        ny1.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

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