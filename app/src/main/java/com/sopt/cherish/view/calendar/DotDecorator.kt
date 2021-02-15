package com.sopt.cherish.view.calendar

import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class DotDecorator(
    private val color: Int,
    private val dates: CalendarDay
) : DayViewDecorator {

    override fun shouldDecorate(day: CalendarDay?): Boolean = dates == day

    override fun decorate(view: DayViewFacade) {
        val paintColor = color
        view.addSpan(MultipleDotSpan(5f, paintColor))
    }
}