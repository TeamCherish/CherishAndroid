package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path

// Request param id
// MyPage 조회
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

interface MyPageAPI {
    @GET("user/{id}")
    suspend fun fetchUserPage(
        @Path("id") userId: Int
    ): MyPageUserRes
}