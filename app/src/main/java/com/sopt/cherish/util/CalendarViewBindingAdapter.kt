package com.sopt.cherish.util

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.sopt.cherish.R
import com.sopt.cherish.view.calendar.CherishMaterialCalendarView

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
}