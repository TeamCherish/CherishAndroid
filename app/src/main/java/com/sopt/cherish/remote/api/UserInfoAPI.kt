package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.*

data class ResponseUserinfoData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val cherishDetail: CherishDetail,
        val userDetail: UserDetail
    ) {
        data class CherishDetail(
            val UserId: Int,
            val cycle_date: Int,
            val notice_time: String
        )

        data class UserDetail(
            val birth: String,
            val name: String,
            val nickname: String,
            val phone: String
        )
    }
}
data class RequestUserinfoData(
    val CherishId : Int
)
interface UserInfoAPI {
    @Headers("Content-Type:application/json")
    @HTTP(method = "GET", path = "getUserDetail", hasBody = true)

    fun getUserInfo(
        @Query("id") id: Int,
        @Body body: RequestUserinfoData
    ): Call<ResponseUserinfoData>
}