package com.sopt.cherish.ui.dialog

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.EventLog
import android.util.Log
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
    lateinit var listminute: ArrayList<String>


    interface TestDialogFragmentListener {
        fun onTestDialogClock(dialog: DialogFragment?, someData: String?)
    }

    var testDialogFragmentListener: TestDialogFragmentListener? = null

    fun someAction() {
        testDialogFragmentListener!!.onTestDialogClock(
            this@ClockPickerDialogFragment, clocktext
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testDialogFragmentListener = context as TestDialogFragmentListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // testDialogFragmentListener = activity as TestDialogFragmentListener

        val binding = ClockpickerLayoutBinding.bind(view)
        val clock_hour: NumberPicker = view.findViewById(R.id.numberPicker_clock)
        val clock_minute: NumberPicker = view.findViewById(R.id.numberPicker2_clock)
        val clock_ampm: NumberPicker = view.findViewById(R.id.numberPicker3_clock)

        val cancel: Button = view.findViewById(R.id.button_cancel_clock)
        cancel.setOnClickListener {
            dismiss()

        }
        val list = resources.getStringArray(R.array.ampm)


        clock_hour.removeDivider()
        clock_minute.removeDivider()
        clock_ampm.removeDivider()

        clock_hour.minValue = 1
        clock_hour.maxValue = 12

        clock_minute.minValue = 0
        clock_minute.maxValue = 59

        clock_ampm.minValue = 0
        clock_ampm.maxValue = list.size - 1

        clock_ampm.displayedValues = list

        clock_hour.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clock_ampm.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        clock_minute.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS

        val btn_ok: Button = view.findViewById(R.id.button_ok_clock)
        btn_ok.setOnClickListener {
            Log.d("clock", clock_ampm.value.toString())
            if ((clock_minute.value.toString()).length < 2) {
                if (list[clock_ampm.value] == "AM") {
                    clocktext =
                        clock_hour.value.toString() + ":0" + clock_minute.value.toString() + " " + list[clock_ampm.value]
                    Log.d("clock1", clocktext.toString())
                    someAction()
                } else {
                    val clock = clock_hour.value + 12
                    clocktext =
                        clock.toString() + ":0" + clock_minute.value.toString() + " " + list[clock_ampm.value]
                    Log.d("clock1", clocktext.toString())
                    someAction()
                }
            } else {
                if (list[clock_ampm.value] == "PM") {
                    val clock = clock_hour.value + 12
                    clocktext =
                        clock.toString() + ":" + clock_minute.value.toString() + " " + list[clock_ampm.value]
                    Log.d("clock2", clocktext.toString())

                    someAction()
                } else {
                    clocktext =
                        clock_hour.value.toString() + ":" + clock_minute.value.toString() + " " + list[clock_ampm.value]
                    Log.d("clock2", clocktext.toString())

                    someAction()
                }
            }


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