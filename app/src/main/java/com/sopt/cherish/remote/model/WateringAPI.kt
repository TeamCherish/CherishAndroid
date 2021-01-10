package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

// 물주기 미루기 , 물주기 미루기가 3회 미만인지 check
data class PostPoneWateringReq(
    @SerializedName("id") val id: Int,
    @SerializedName("postpone") val postpone: Int,
    @SerializedName("is_limit_postpone_number") val postponeCountingNumber: Int,
)

data class PostPoneWateringRes(
    val postponeWateringResponse: UtilResponse
)

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
)
