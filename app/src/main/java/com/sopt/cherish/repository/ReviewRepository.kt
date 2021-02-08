package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.ReviewAPI
import com.sopt.cherish.remote.api.ReviewWateringReq

class ReviewRepository(
    private val reviewAPI: ReviewAPI
) {
    suspend fun sendReviewData(reviewWateringReq: ReviewWateringReq) =
        reviewAPI.reviewWatering(reviewWateringReq)
}