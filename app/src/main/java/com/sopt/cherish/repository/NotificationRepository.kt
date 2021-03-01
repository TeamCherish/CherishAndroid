package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.NotificationAPI
import com.sopt.cherish.remote.api.NotificationReq

class NotificationRepository(
    private val notificationAPI: NotificationAPI
) {
    suspend fun sendFcmToken(notificationReq: NotificationReq) =
        notificationAPI.sendFcmToken(notificationReq)
}