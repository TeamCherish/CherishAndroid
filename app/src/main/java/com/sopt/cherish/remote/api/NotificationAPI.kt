package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.PUT

data class NotificationReq(
    @SerializedName("id") val userId: Int,
    @SerializedName("fcm_token") val fcmToken: String
)

interface NotificationAPI {
    @PUT("user/token")
    suspend fun sendFcmToken(
        @Body notificationReq: NotificationReq
    ): UtilResponseWithOutStatus
}