package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

// request param 필요 , 리뷰 물주기
data class ReviewWateringReq(
    @SerializedName("water_date") val waterDate: Date,
    @SerializedName("review") val review: String,
    @SerializedName("keyword1") val userStatus1: String,
    @SerializedName("keyword2") val userStatus2: String,
    @SerializedName("keyword3") val userStatus3: String,
    @SerializedName("user_id") val userId: Int,
)

data class ReviewWateringRes(
    // 이거 질문 있음
    val reviewWateringResponse: UtilResponse,
    @SerializedName("data") val increasedCount: Int
)

interface ReviewAPI {
    @POST("/water")
    fun reviewWatering(
        @Body reviewWateringReq: ReviewWateringReq
    ): ReviewWateringRes
}