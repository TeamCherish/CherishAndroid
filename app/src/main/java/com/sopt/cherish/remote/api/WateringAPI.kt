package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

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

// 물주기 미루기
data class PostponeWateringDateReq(
    @SerializedName("id") val id: Int,
    @SerializedName("postpone") val postpone: Int,
    @SerializedName("is_limit_postpone_number") val isPostpone: Boolean
)

data class PostponeWateringDateRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

interface WateringAPI {
    @GET("postpone")
    suspend fun getPostponeWateringCount(
        @Query("CherishId") cherishId: Int
    ): PostponeWateringRes

    @PUT("postpone")
    suspend fun postponeWateringDate(
        @Body postponeWateringDateReq: PostponeWateringDateReq
    ): PostponeWateringRes
}