package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName

// 물주기 미루기 , 물주기 미루기가 3회 미만인지 check
data class PostPoneWateringReq(
    @SerializedName("id") val id: Int,
    @SerializedName("postpone") val postpone: Int,
    @SerializedName("is_limit_postpone_number") val postponeCountingNumber: Int,
)

data class PostPoneWateringRes(
    val postponeWateringResponse: UtilResponse
)



interface WateringAPI