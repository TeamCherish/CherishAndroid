package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

// Request param id
// MyPage 조회
data class MyPageUserData(
    @SerializedName("postponeCount") val postponeCount: Int,
    @SerializedName("waterCount") val waterCount: Int,
    @SerializedName("completeCount") val completeCount: Int,
    @SerializedName("totalCherish") val totalCherish: Int,
    @SerializedName("result") val result: List<MyPageCherishData>
)

data class MyPageUserRes(
    val myPageUserResponse: UtilResponse,
    @SerializedName("data") val myPageUserData: MyPageUserData
)

data class MyPageCherishData(
    @SerializedName("id") val id: Int,
    @SerializedName("dDay") val dDay: Int,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("name") val name: String,
    @SerializedName("thumbnail_image_url") val thumbnailImageUrl: String,
    @SerializedName("level") val level: String,
    @SerializedName("PlantId") val plantId: Int
)

interface MyPageAPI {
    @GET("user/{id}")
    fun fetchUserPage(
        @Path("id") userId: Int
    ): Call<MyPageUserRes>
}
