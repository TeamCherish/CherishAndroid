package com.sopt.cherish.ui.dialog

import android.app.Activity
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
class ClockPickerDialogFragment(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {

    lateinit var clocktext: String

    interface TestDialogFragmentListener {
        fun onTestDialogClock(dialog: DialogFragment?, someData: String?)
    }

    var testDialogFragmentListener: TestDialogFragmentListener? = null
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        testDialogFragmentListener = try {
            activity as TestDialogFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement TestDialogFragmentListener"
            )
        }
    }

    fun someAction() {
        testDialogFragmentListener!!.onTestDialogClock(
            this@ClockPickerDialogFragment, clocktext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val binding = ClockpickerLayoutBinding.bind(view)
        val clockHour: NumberPicker = view.findViewById(R.id.numberPicker_clock)
        val clockMinute: NumberPicker = view.findViewById(R.id.numberPicker2_clock)
        val clockAmPm: NumberPicker = view.findViewById(R.id.numberPicker3_clock)

        val cancel: Button = view.findViewById(R.id.button_cancel_clock)
        cancel.setOnClickListener {
            dismiss()

        }
        val list = resources.getStringArray(R.array.ampm)


        clockHour.removeDivider()
        clockMinute.removeDivider()
        clockAmPm.removeDivider()

        clockHour.minValue = 1
        clockHour.maxValue = 12

        clockMinute.minValue = 0
        clockMinute.maxValue = 59

        clockAmPm.minValue = 0
        clockAmPm.maxValue = list.size - 1

        clockAmPm.displayedValues = list

        clockHour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clockAmPm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clockMinute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        val btn_ok: Button = view.findViewById(R.id.button_ok_clock)
        btn_ok.setOnClickListener {

            clocktext =
                clockHour.value.toString() + ":" + clockMinute.value.toString() + " " + list[clockAmPm.value]
            someAction()
            dialog?.dismiss()

        }
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