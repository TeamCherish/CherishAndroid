package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

// 로그인 req
data class EditUserReq(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// 로그인 res
data class EditUserRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val editUserData: EditUserData
)

data class EditUserData(
    @SerializedName("UserId") val userId: Int,
    @SerializedName("user_nickname") val userNickName: String,
    @SerializedName("token") val token: String
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

interface AuthAPI {
    @Headers("Content-Type:application/json")
    @POST("login/signin")
    fun postLogin(
        @Body body: EditUserReq
    ): Call<EditUserRes>
}
