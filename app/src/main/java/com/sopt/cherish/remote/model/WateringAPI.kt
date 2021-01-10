package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

data class PostPoneWateringReq(
    @SerializedName("id") val id: Int,
    @SerializedName("postpone") val postpone: Int,
    @SerializedName("is_limit_postpone_number") val postponeCountingNumber: Int,
)

data class PostPoneWateringRes(
    val postponeWateringResponse: UtilResponse
)

// request param 필요
