package com.sopt.cherish.remote.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

const val serverKey =
    "AAAAJ13TVxs:APA91bHoV_xPXOYoRT5Hs0-LH6ew179hHqHQJLzb4sKFphe202XAnlGqW09xdQKZP4YWwPK7v7MGDIZnSXJJiriMzr1trnMNzGgq2UiOmH3Pw7hR_pDaDhaL7oxGrHrrg6C8dYLBrTvQ"

const val contentType = "application/json"

data class NotificationData(
    val title: String,
    val message: String
)

data class PushNotification(
    val data: NotificationData,
    val to: String
)

interface NotificationAPI {

    @Headers("Authorization: key=$serverKey", "Content-Type:$contentType")
    @POST("fcm/send")
    suspend fun postNotification(
        @Body notification: PushNotification
    ): Response<ResponseBody>


}