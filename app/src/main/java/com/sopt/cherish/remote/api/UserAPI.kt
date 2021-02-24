package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * created on 01-11 by SSong-develop
 * 진행 중인 식물을 등록 했을 때
 */
data class UserNickName(
    @SerializedName("nickname") val nickName: String
)

data class UserId(
    @SerializedName("UserId") val userId: Int
)

// main 화면 user
data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("dDay") val dDay: Int,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("phone") val phoneNumber: String,
    @SerializedName("growth") val growth: Int,
    @SerializedName("image_url") val plantImageUrl: String,
    @SerializedName("thumbnail_image_url") val thumbnailImageUrl: String,
    @SerializedName("plantName") val plantName: String,
    @SerializedName("gif") val plantAnimationUrl: String,
    @SerializedName("main_bg") val homeMainBackgroundImageUrl: String,
    @SerializedName("modifier") val plantModifier: String
)

data class UserData(
    @SerializedName("result") val userList: MutableList<User>,
    @SerializedName("totalCherish") val totalUser: Int
)

// main user result
data class UserResult(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userData: UserData
)

/**
 * Retrofit Service가 이 안에 들어갈겁니다.
 */
interface UserAPI {
    @GET("cherish/{id}")
    suspend fun getCherishUser(
        @Path("id") cherishId: Int
    ): UserResult

    @GET("cherish/{id}")
    fun hasUser(
        @Path("id") userId: Int
    ): Call<UserResult>
}