package com.sopt.cherish.util

import android.annotation.SuppressLint
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.flexbox.FlexboxLayout
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.sopt.cherish.R
import com.sopt.cherish.remote.api.CalendarRes
import com.sopt.cherish.util.extension.FlexBoxExtension.addChip
import com.sopt.cherish.view.calendar.CherishMaterialCalendarView
import com.sopt.cherish.view.calendar.DotDecorator

object CalendarViewBindingAdapter {
    @JvmStatic
    @BindingAdapter("android:calendarAllowChange")
    fun calendarAllowChange(imageButton: ImageButton, focus: Boolean) {
        if (focus) {
            imageButton.setImageResource(R.drawable.icn_allow_top)
        } else {
            imageButton.setImageResource(R.drawable.icn_allow)
        }
    }

    @JvmStatic
    @BindingAdapter("android:calendarModeChange")
    fun calendarModeChange(
        cherishMaterialCalendarView: CherishMaterialCalendarView,
        allowMode: Boolean
    ) {
        if (allowMode)
            cherishMaterialCalendarView.changeCalendarModeWeeks()
        else
            cherishMaterialCalendarView.changeCalendarModeMonths()
    }

    @JvmStatic
    @BindingAdapter("android:addDecorator")
    fun addDecorator(
        cherishMaterialCalendarView: CherishMaterialCalendarView,
        calendarRes: CalendarRes
    ) {

        calendarRes.waterData.calendarData.forEach {

        cherishMaterialCalendarView.addDecorator(
                DotDecorator(
                    ContextCompat.getColor(
                        cherishMaterialCalendarView.context,
                        R.color.cherish_green_sub
                    ), DateUtil.convertDateToCalendarDay(it.wateredDate)
                )
            )
        }
        cherishMaterialCalendarView.addDecorator(
            DotDecorator(
                ContextCompat.getColor(
                    cherishMaterialCalendarView.context,
                    R.color.cherish_pink_sub
                ), DateUtil.convertDateToCalendarDay(calendarRes.waterData.futureWaterDate)
            )
        )
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("android:showDate")
    fun showDate(textView: TextView, date: CalendarDay?) {
        textView.text = "${date?.year}년 ${date?.month}월 ${date?.day}일"
    }

    @JvmStatic
    @BindingAdapter("android:showChips")
    fun showChips(flexboxLayout: FlexboxLayout, chipsText: List<String>?) {
        chipsText?.forEach {
            flexboxLayout.addChip(it)
        }
    }

    @JvmStatic
    @BindingAdapter("android:showMemo")
    fun showMemo(textView: TextView, memoText: String?) {
        textView.text = " "
        textView.text = memoText
    }

}