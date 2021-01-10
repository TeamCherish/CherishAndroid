package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

// Request param id
data class MyPageUserData(
    @SerializedName("postponeCount") val postponeCount: Int,
    @SerializedName("waterCount") val waterCount: Int,
    @SerializedName("completeCount") val compleCount: Int,
    @SerializedName("totalCherish") val totalCherish: Int,
    @SerializedName("result") val result: List<Any>
)

data class MyPageUserRes(
    val myPageUserResponse: UtilResponse,
    @SerializedName("data") val myPageUserData: MyPageUserData
)