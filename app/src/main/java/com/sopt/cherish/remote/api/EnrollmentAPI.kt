package com.sopt.cherish.remote.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

data class RequestEnrollData(
    val name: String,
    val nickname: String,
    val birth: String,
    val phone: String,
    val cycle_date: Int,
    val notice_time: String,
    val water_notice: Boolean,
    val UserId: Int

)


data class ResponseEnrollData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val plant: Plant
    ) {
        data class Plant(
            val PlantStatusId: Int,
            val explanation: String,
            val flower_meaning: String,
            val id: Int,
            val image_url: String,
            val modifier: String,
            val name: String,
            val thumbnail_image_url: String
        )
    }
}

interface EnrollmentAPI {
    @POST("/cherish")
    @Headers("Content-Type:application/json")
    fun enrollCherish(
        @Body enrollCherishReq: RequestEnrollData
    ): Call<ResponseEnrollData>
}