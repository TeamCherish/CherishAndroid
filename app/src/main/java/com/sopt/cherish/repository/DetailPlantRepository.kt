package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.*

/**
 * Created on 01-07 by SSong-develop
 */
class DetailPlantRepository(
    private val calendarAPI: CalendarAPI,
    private val reviewAPI: ReviewAPI
) {
    // repository가 알고있어야 할 것은 retrofitService interface
    suspend fun fetchCalendarData(cherishId: Int): CalendarRes {
        return calendarAPI.getCalendarData(cherishId)
    }

    suspend fun sendReviewData(reviewWateringReq: ReviewWateringReq) =
        reviewAPI.reviewWatering(reviewWateringReq)

    suspend fun sendReviseReviewData(reviseReviewReq: ReviseReviewReq) =
        reviewAPI.reviseReview(reviseReviewReq)

    suspend fun deleteReviewData(deleteReviewReq: DeleteReviewReq) =
        reviewAPI.deleteReview(deleteReviewReq)
}