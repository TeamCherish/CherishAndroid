package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.*

// request param 필요 , 리뷰 물주기
// data class 이름 바꿔라 userId가 아니라 CherishId 잖아 훈기야
data class ReviewWateringReq(
    @SerializedName("review") val review: String?,
    @SerializedName("keyword1") val userStatus1: String?,
    @SerializedName("keyword2") val userStatus2: String?,
    @SerializedName("keyword3") val userStatus3: String?,
    @SerializedName("CherishId") val userId: Int
)

data class ReviewWateringData(
    @SerializedName("user_nickname") val nickName: String,
    @SerializedName("cherish_nickname") val cherishNickName: String,
    @SerializedName("score") val score: Int
)

data class ReviewWateringRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val reviewScore: Int
)

// Revise Review Data
data class ReviseReviewReq(
    @SerializedName("CherishId") val cherishId: Int,
    @SerializedName("water_date") val waterDate: String,
    @SerializedName("review") val review: String?,
    @SerializedName("keyword1") val userStatus1: String?,
    @SerializedName("keyword2") val userStatus2: String?,
    @SerializedName("keyword3") val userStatus3: String?,
)

// Delete Review Data
data class DeleteReviewReq(
    @SerializedName("CherishId") val cherishId: Int,
    @SerializedName("water_date") val waterDate: String
)

interface ReviewAPI {
    @Headers("Content-Type:application/json")
    @POST("water")
    suspend fun reviewWatering(
        @Body reviewWateringReq: ReviewWateringReq
    ): ReviewWateringRes

    @PUT("calendar")
    suspend fun reviseReview(
        @Body reviseReviewReq: ReviseReviewReq
    ): UtilResponseWithOutStatus

    @HTTP(method = "DELETE", path = "calendar", hasBody = true)
    suspend fun deleteReview(
        @Body deleteReviewReq: DeleteReviewReq
    ): UtilResponseWithOutStatus
}