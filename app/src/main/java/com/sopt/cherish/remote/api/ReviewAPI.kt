package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// request param 필요 , 리뷰 물주기
data class ReviewWateringReq(
    @SerializedName("review") val review: String?,
    @SerializedName("keyword1") val userStatus1: String?,
    @SerializedName("keyword2") val userStatus2: String?,
    @SerializedName("keyword3") val userStatus3: String?,
    @SerializedName("CherishId") val userId: Int,
)

data class ReviewWateringData(
    @SerializedName("user_nickname") val nickName: String,
    @SerializedName("cherish_nickname") val cherishNickName: String,
    @SerializedName("score") val score: Int
)

data class ReviewWateringRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val reviewWateringData: ReviewWateringData
)

interface ReviewAPI {
    @Headers("Content-Type:application/json")
    @POST("water")
    suspend fun reviewWatering(
        @Body reviewWateringReq: ReviewWateringReq
    ): ReviewWateringRes
}