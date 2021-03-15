package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.*

/**
 * Created on 01-07 by SSong-develop
 */
class DetailPlantRepository(
    private val calendarAPI: CalendarAPI,
    private val reviewAPI: ReviewAPI
) {
    suspend fun fetchCalendarData(cherishId: Int): CalendarRes {
        return calendarAPI.getCalendarData(cherishId)
    }

    suspend fun sendReviseReviewData(reviseReviewReq: ReviseReviewReq) =
        reviewAPI.reviseReview(reviseReviewReq)

    suspend fun deleteReviewData(deleteReviewReq: DeleteReviewReq) =
        reviewAPI.deleteReview(deleteReviewReq)
}