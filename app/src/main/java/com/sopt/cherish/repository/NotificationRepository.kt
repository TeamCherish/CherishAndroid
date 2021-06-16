package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.NotificationAPI
import com.sopt.cherish.remote.api.NotificationRemindReviewReq
import com.sopt.cherish.remote.api.NotificationReq

class NotificationRepository(
    private val notificationAPI: NotificationAPI
) {
    suspend fun sendFcmToken(notificationReq: NotificationReq) =
        notificationAPI.sendFcmToken(notificationReq)

    suspend fun sendRemindReviewNotification(notificationRemindReviewReq: NotificationRemindReviewReq) =
        notificationAPI.sendRemindReviewNotification(notificationRemindReviewReq)

    suspend fun sendRemindNotification(cherishId: Int) =
        notificationAPI.remindNotification(cherishId)
}