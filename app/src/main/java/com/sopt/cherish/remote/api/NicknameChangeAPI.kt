package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

data class ResponseNicknameChangedata(
    val success: Boolean,
    val message: String
)


data class RequestNicknameData(
    val id: Int,
    val nickname: String
)


interface NicknameChangeAPI {
    @Headers("Content-Type:application/json")
    @PUT("addView")
    fun nicknamechange(
        @Body body: RequestNicknameData
    ): Call<ResponseNicknameChangedata>
}