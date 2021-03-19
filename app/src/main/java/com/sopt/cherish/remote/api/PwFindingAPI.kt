package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RequestPwFindingData(
    val email: String
)

data class VerifyCode(
    @SerializedName("verifyCode") val verifyCode: Int
)

data class ResponsePwFindingData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val data: VerifyCode
)

interface PwFindingAPI {
    @POST("login/findPassword")
    fun postPwFinding(
        @Body body: RequestPwFindingData
    ): Call<ResponsePwFindingData>
}
