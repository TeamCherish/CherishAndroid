package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HTTP

data class ResponseUserDeleteData(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class RequestUserDeleteData(
    val id: Int
)

interface UserDeleteAPI {
    @HTTP(method = "DELETE", path = "user", hasBody = true)
    fun deleteUser(
        @Body body: RequestUserDeleteData
    ): Call<ResponseUserDeleteData>
}