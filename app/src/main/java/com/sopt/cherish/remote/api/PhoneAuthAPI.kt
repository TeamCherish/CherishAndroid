package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RequestPhoneAuthData (
    val phone:String
)

data class ResponsePhoneAuthData(
    @SerializedName("success") val success:Boolean,
    @SerializedName("message") val message:String,
    @SerializedName("data") val data:Int
)

interface PhoneAuthAPI{
    @POST("login/phoneAuth")
    fun postAuth(
        @Body body:RequestPhoneAuthData
    ): Call<ResponsePhoneAuthData>
}