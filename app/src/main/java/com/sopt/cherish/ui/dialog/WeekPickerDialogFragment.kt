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
class WeekPickerDialogFragment(
    @LayoutRes
    private val layoutResId: Int
) : DialogFragment(), View.OnClickListener {

    lateinit var weektext: String

    interface TestDialogFragmentListener {
        fun onTestDialogweek(dialog: DialogFragment?, someData: String?)
    }

    var testDialogFragmentListener: TestDialogFragmentListener? = null

    fun someAction() {
        testDialogFragmentListener!!.onTestDialogweek(
            this@WeekPickerDialogFragment, weektext
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layoutResId, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        testDialogFragmentListener = activity as TestDialogFragmentListener

        val binding = WeekpickerLayoutBinding.bind(view)


        val week_every: NumberPicker = view.findViewById(R.id.numberPicker)
        val week_number: NumberPicker = view.findViewById(R.id.numberPicker2)
        val week_month: NumberPicker = view.findViewById(R.id.numberPicker3)

        val cancel: Button = view.findViewById(R.id.button_alarm)
        cancel.setOnClickListener {
            dismiss()

        }


        val list_cycle = resources.getStringArray(R.array.cycle)
        val list_every = resources.getStringArray(R.array.week)

        week_every.removeDivider()
        week_number.removeDivider()
        week_month.removeDivider()

        week_every.minValue = 0
        week_every.maxValue = list_every.size - 1

        week_number.minValue = 1
        week_number.maxValue = 3

        week_month.minValue = 0
        week_month.maxValue = list_cycle.size - 1

        week_every.displayedValues = list_every
        week_month.displayedValues = list_cycle

        week_every.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        week_month.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        week_number.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        val btn_ok: Button = view.findViewById(R.id.button_ok)
        btn_ok.setOnClickListener {

            weektext =
                list_every[week_every.value] + " " + week_number.value.toString() + " " + list_cycle[week_month.value]
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