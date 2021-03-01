package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

data class ResponseUserinfoData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val cherishDetail: CherishDetail,
    ) {
        data class CherishDetail(
            val nickname: String,
            val birth: String,
            val phone: String,
            val cycle_date: Int,
            val notice_time: String,
            val water_notice: Boolean
        )

    }
}

data class RequestUserinfoData(
    val CherishId: Int
)

interface UserInfoAPI {
    @Headers("Content-Type:application/json")
    @GET("getCherishDetail/{CherishId}")
    fun getUserInfo(
        @Path("CherishId") CherishId: Int,
    ): Call<ResponseUserinfoData>
}