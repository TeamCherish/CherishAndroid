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
import com.sopt.cherish.util.extension.FlexBoxExtension.addChipCalendar
import com.sopt.cherish.util.extension.FlexBoxExtension.clearChips
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
        date?.let {
            textView.text = "${it.year}년 ${it.month}월 ${it.day}일"
        }
    }

    @JvmStatic
    @BindingAdapter(
        value = ["android:userStatus1", "android:userStatus2", "android:userStatus3"],
        requireAll = false
    )
    fun showChips(
        flexboxLayout: FlexboxLayout,
        userStatus1: String?,
        userStatus2: String?,
        userStatus3: String?
    ) {
        flexboxLayout.clearChips()
        flexboxLayout.apply {
            if (userStatus1 != "null" && userStatus1 != " ") {
                userStatus1?.let { addChipCalendar(it) }
            }
            if (userStatus2 != "null" && userStatus2 != " ") {
                userStatus2?.let { addChipCalendar(it) }
            }
            if (userStatus3 != "null" && userStatus3 != " ") {
                userStatus3?.let { addChipCalendar(it) }
            }
        }
    }

    @JvmStatic
    @BindingAdapter("android:showMemo")
    fun showMemo(textView: TextView, memoText: String?) {
        textView.text = " "
        textView.text = memoText
    }

}