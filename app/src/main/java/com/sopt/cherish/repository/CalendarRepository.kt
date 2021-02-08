package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.CalendarAPI

class CalendarRepository(
    private val calendarAPI: CalendarAPI
) {
    suspend fun getChipsData(cherishId: Int) = calendarAPI.getCalendarData(cherishId)
}