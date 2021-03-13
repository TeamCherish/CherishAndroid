package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RequestUpdatePasswordAPI(
    val email:String,
    val password1:String,
    val password2:String
)

data class ResponseUpdatePasswordAPI(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message:String
)

interface UpdatePasswordAPI {
    @POST("login/updatePassword")
    fun postPwFUpdate(
        @Body body: RequestUpdatePasswordAPI
    ): Call<ResponseUpdatePasswordAPI>
}