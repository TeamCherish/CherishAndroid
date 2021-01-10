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

// 회원가입 request
data class CreateUserReq(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("sex") val sex: Boolean,
    @SerializedName("birth") val birth: String
)

// 회원가입 response
data class CreateUserRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userNickName: UserNickName
)

// 로그인 req
data class EditUserReq(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// 로그인 res
data class EditUserRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userId: UserId
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

data class UserResult(
    @SerializedName("result") val userResult: List<User>,
    @SerializedName("totalCherish") val totalUser: Int
)

// main user response
data class UserRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val userResult: UserResult
)

/**
 * Retrofit Service가 이 안에 들어갈겁니다.
 */
interface UserAPI