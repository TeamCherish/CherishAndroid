package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class ResponseCheckPhoneData(
    val success: Boolean,
    val message: String
)
data class RequestCheckPhoneData(
    val phone: String,
    val UserId: Int
)
interface CheckPhoneAPI {
    @Headers("Content-Type:application/json")
    @POST("cherish/checkPhone")
    fun checkphone(
        @Body body: RequestCheckPhoneData
    ): Call<ResponseCheckPhoneData>
}