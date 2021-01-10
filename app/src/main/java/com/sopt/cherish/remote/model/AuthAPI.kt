package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

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

interface AuthAPI
