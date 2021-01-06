package com.sopt.cherish.util

import com.sopt.cherish.MainApplication
import com.sopt.cherish.view.calendar.CherishMaterialCalendarView

object CalendarUtil {
    fun adjustCalendarSize(
        calendar: CherishMaterialCalendarView,
        widthRatio: Float,
        heightRatio: Float
    ) {
        val calendarParams = calendar.layoutParams

        (MainApplication.pixelRatio.screenWidth * widthRatio).toInt()
            .also { calendarParams.width = it }
        (MainApplication.pixelRatio.screenHeight * heightRatio).toInt()
            .also { calendarParams.height = it }

    }
}