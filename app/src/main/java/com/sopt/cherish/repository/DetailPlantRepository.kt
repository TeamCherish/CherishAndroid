package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.CalendarAPI
import com.sopt.cherish.remote.api.CalendarRes
import com.sopt.cherish.util.DateUtil
import com.sopt.cherish.util.SimpleLogger

/**
 * Created on 01-07 by SSong-develop
 */
class DetailPlantRepository(
    private val calendarAPI: CalendarAPI
) {
    // repository가 알고있어야 할 것은 retrofitService interface
    suspend fun fetchCalendarData(cherishId: Int): CalendarRes {
        SimpleLogger.logI(calendarAPI.getCalendarData(1).waterData.calendarData.forEach {
            DateUtil.convertDateToString(it.wateredDate)
        }.toString())
        return calendarAPI.getCalendarData(cherishId)
    }
}