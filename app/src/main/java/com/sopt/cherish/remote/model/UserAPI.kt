package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

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
    @SerializedName("growth") val growth: Int,
    @SerializedName("image_url") val userPlantImageUrl: String,
    @SerializedName("thumbnail_image_url") val thumbnailImageUrl: String
)

data class UserData(
    @SerializedName("result") val userResult: List<User>,
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
interface UserAPI