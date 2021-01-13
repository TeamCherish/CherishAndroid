package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// 물주기 미루기 , 물주기 미루기가 3회 미만인지 check
data class PostponeWateringData(
    @SerializedName("cherish") val wateredDateAndPostponeCount: WateredDateAndPostponeCount,
    @SerializedName("is_limit_postpone_number") val isPostpone: Boolean
)

data class WateredDateAndPostponeCount(
    @SerializedName("water_date") val waterDate: String,
    @SerializedName("postpone_number") val postponeCount: Int
)

data class PostponeWateringRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val postponeData: PostponeWateringData
)

interface WateringAPI {
    @GET("postpone")
    suspend fun getPostponeWateringCount(
        @Query("CherishId") cherishId: Int
    ): PostponeWateringRes
}