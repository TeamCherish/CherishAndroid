package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

// request param cherishId
// keyword , Review 조회하기 다시 물어보기 의문점 있음
data class CalendarData(
    @SerializedName("water_date") val wateredDate: Date,
    @SerializedName("review") val review: String,
    @SerializedName("keyword1") val userStatus1: String,
    @SerializedName("keyword2") val userStatus2: String,
    @SerializedName("keyword3") val userStatus3: String
)

data class WaterData(
    @SerializedName("water") val calendarData: List<CalendarData>,
    @SerializedName("future_water_date") val futureWaterDate: Date
)

data class CalendarRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val waterData: WaterData
)

interface CalendarAPI {
    @GET("calendar/{id}")
    suspend fun getCalendarData(
        @Path("id") cherishId: Int
    ): CalendarRes
}