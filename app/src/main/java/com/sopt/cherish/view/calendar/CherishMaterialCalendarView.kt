package com.sopt.cherish.view.calendar

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

/**
 * Created on 01-06 by SSong-develop
 * default MaterialCalendarView height = 372dp
 */
class CherishMaterialCalendarView constructor(context: Context, attrs: AttributeSet) :
    MaterialCalendarView(context, attrs) {

    fun changeCalendarModeWeeks() {
        state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
    }

    fun changeCalendarModeMonths() {
        state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
    }

}