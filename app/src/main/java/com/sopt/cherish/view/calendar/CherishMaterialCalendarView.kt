package com.sopt.cherish.view.calendar

import android.content.Context
import android.util.AttributeSet
import com.prolificinteractive.materialcalendarview.CalendarMode
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.sopt.cherish.util.PixelUtil.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created on 01-06 by SSong-develop
 * defalut MaterialCalendarView height = 372dp
 */
class CherishMaterialCalendarView constructor(context: Context, attrs: AttributeSet) :
    MaterialCalendarView(context, attrs) {

    fun changeCalendarModeWeeks() {
        CoroutineScope(Dispatchers.Main).launch {
            state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit()
            layoutParams.height = 150.dp
        }
    }

    fun changeCalendarModeMonths() {
        CoroutineScope(Dispatchers.Main).launch {
            state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit()
            layoutParams.height = 372.dp
        }
    }

}