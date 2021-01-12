package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.*
import com.sopt.cherish.util.SimpleLogger

/**
 * Created on 01-07 by SSong-develop
 */
class MainRepository(
    private val userAPI: UserAPI,
    private val myPageAPI: MyPageAPI,
    private val reviewAPI: ReviewAPI
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        SimpleLogger.logI(userAPI.getCherishUser(1).toString())
        return userAPI.getCherishUser(userId)
    }
/*
    suspend fun fetchCherishUserPageData(userId: Int): MyPageUserRes {
        return myPageAPI.fetchUserPage(userId)
    } */

    suspend fun sendReviewData(reviewWateringReq: ReviewWateringReq) =
        reviewAPI.reviewWatering(reviewWateringReq)
}