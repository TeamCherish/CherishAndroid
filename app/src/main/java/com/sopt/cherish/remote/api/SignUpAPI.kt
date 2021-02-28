package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RequestSignUpData (
    val email:String,
    val password:String,
    val nickname:String,
    val phone:String,
    val sex:String,
    val birth:String
    )

data class Nickname(
    @SerializedName("nickname") val nickname:String
)

data class ResponseSignUpData(
    @SerializedName("success") val success:Boolean,
    @SerializedName("message") val message:String,
    @SerializedName("data") val nickname:Nickname
    )

interface SignUpAPI{
    @POST("login/signup")
    fun postSignUp(
        @Body body:RequestSignUpData
    ): Call<ResponseSignUpData>
}

