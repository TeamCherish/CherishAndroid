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
        /*layoutParams.height = 150.dp*/
    }

    fun changeCalendarModeMonths() {
        state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
        /*layoutParams.height = 388.dp*/
    }

}