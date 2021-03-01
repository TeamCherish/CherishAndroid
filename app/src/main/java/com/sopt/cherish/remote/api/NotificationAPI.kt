package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

data class NotificationReq(
    @SerializedName("id") val userId: Int,
    @SerializedName("fcm_token") val fcmToken: String
)

data class NotificationWateringReq(
    @SerializedName("CherishId") val cherishId: Int
)

interface NotificationAPI {
    @PUT("user/token")
    suspend fun sendFcmToken(
        @Body notificationReq: NotificationReq
    ): UtilResponseWithOutStatus

    @POST("push")
    suspend fun sendRefreshedWateringTimeNotification(
        @Body notificationWateringReq: NotificationWateringReq
    ): UtilResponseWithOutStatus


}