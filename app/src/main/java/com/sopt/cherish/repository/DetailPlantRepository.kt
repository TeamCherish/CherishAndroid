package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.CalendarAPI
import com.sopt.cherish.remote.api.CalendarRes

/**
 * Created on 01-07 by SSong-develop
 */
class DetailPlantRepository(
    private val calendarAPI: CalendarAPI
) {
    // repository가 알고있어야 할 것은 retrofitService interface
    suspend fun fetchCalendarData(cherishId: Int): CalendarRes {
        return calendarAPI.getCalendarData(cherishId)
    }
}