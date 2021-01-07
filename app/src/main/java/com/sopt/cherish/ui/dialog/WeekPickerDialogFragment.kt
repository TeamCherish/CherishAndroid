package com.sopt.cherish.ui.dialog

import android.content.Context
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

    private var testDialogFragmentListener: TestDialogFragmentListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        testDialogFragmentListener = try {
            activity as TestDialogFragmentListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                activity.toString()
                        + " must implement TestDialogFragmentListener"
            )
        }
    }

    // todo : 어떤 액션인지 자세하게 얘기
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

        val binding = WeekpickerLayoutBinding.bind(view)
        // todo : 왜 findViewById 인지?
        val weekEvery: NumberPicker = view.findViewById(R.id.numberPicker)
        val weekNumber: NumberPicker = view.findViewById(R.id.numberPicker2)
        val weekMonth: NumberPicker = view.findViewById(R.id.numberPicker3)

        val cancel: Button = view.findViewById(R.id.button_alarm)
        cancel.setOnClickListener {
            dismiss()
        }
        // todo : 로직 좀 더 깔끔하게
        val list = resources.getStringArray(R.array.cycle)
        val list2 = resources.getStringArray(R.array.week)

        weekEvery.removeDivider()
        weekNumber.removeDivider()
        weekMonth.removeDivider()

        weekEvery.minValue = 0
        weekEvery.maxValue = list2.size - 1

        weekNumber.minValue = 1
        weekNumber.maxValue = 3

        weekMonth.minValue = 0
        weekMonth.maxValue = list.size - 1

        weekEvery.displayedValues = list2
        weekMonth.displayedValues = list

        weekEvery.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        weekMonth.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
        weekNumber.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS


        val btn_ok: Button = view.findViewById(R.id.button_ok)
        btn_ok.setOnClickListener {

            weektext =
                list2[weekEvery.value] + " " + weekNumber.value.toString() + " " + list[weekMonth.value]
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