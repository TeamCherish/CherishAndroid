package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.NotificationAPI
import com.sopt.cherish.remote.api.NotificationWateringReq
import com.sopt.cherish.remote.api.ReviewAPI
import com.sopt.cherish.remote.api.ReviewWateringReq

class ReviewRepository(
    private val reviewAPI: ReviewAPI,
    private val notificationAPI: NotificationAPI
) {
    suspend fun sendReviewData(reviewWateringReq: ReviewWateringReq) =
        reviewAPI.reviewWatering(reviewWateringReq)

    suspend fun sendWateringNotification(notificationWateringReq: NotificationWateringReq) =
        notificationAPI.sendWateringNotification(notificationWateringReq)

}