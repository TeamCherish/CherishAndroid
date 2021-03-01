package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RequestSignUpEmailData(
    val email: String
)

data class ResponseEmailData(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

interface SignUpEmailAPI {
    @POST("checkSameEmail")
    fun postEmail(
        @Body body: RequestSignUpEmailData
    ): Call<ResponseEmailData>
}