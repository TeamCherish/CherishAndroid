package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.*

/**
 * Created on 01-07 by SSong-develop
 */
class MainRepository(
    private val userAPI: UserAPI,
    private val reviewAPI: ReviewAPI,
    private val wateringAPI: WateringAPI,
    private val calendarAPI: CalendarAPI
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        return userAPI.getCherishUser(userId)
    }
/*
    suspend fun fetchCherishUserPageData(userId: Int): MyPageUserRes {
        return myPageAPI.fetchUserPage(userId)
    } */

    suspend fun sendReviewData(reviewWateringReq: ReviewWateringReq) =
        reviewAPI.reviewWatering(reviewWateringReq)

    suspend fun getPostponeCount(cherishId: Int) = wateringAPI.getPostponeWateringCount(cherishId)

    suspend fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        wateringAPI.postponeWateringDate(postponeWateringDateReq)

    suspend fun getChipsData(cherishId: Int) = calendarAPI.getCalendarData(cherishId)

}