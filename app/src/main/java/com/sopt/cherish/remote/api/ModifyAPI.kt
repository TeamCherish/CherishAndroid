package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.*


data class ResponseModifyData(

    val success: Boolean,
    val message: String
)
data class RequestModifyData(
    val nickname: String,
    val birth: String,
    val cycle_date:Integer,
    val notice_time:String,
    val water_notice:Boolean,
    val id :Integer
)

interface ModifyAPI {
    @Headers("Content-Type:application/json")
    @PUT("cherish")
    fun plantmodify(
        @Body body: RequestModifyData
    ): Call<ResponseModifyData>
}