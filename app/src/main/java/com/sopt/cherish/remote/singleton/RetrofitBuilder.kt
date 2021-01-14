package com.sopt.cherish.remote.singleton

import com.sopt.cherish.remote.RetrofitService
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.model.EnrollmentAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BaseUrl = "http://3.35.117.232:8080/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Sample Code
    val retrofitService: RetrofitService = getRetrofit().create(RetrofitService::class.java)

    val userAPI: UserAPI = getRetrofit().create(UserAPI::class.java)

    val myPageAPI: MyPageAPI = getRetrofit().create(MyPageAPI::class.java)

    val notificationAPI: NotificationAPI = getRetrofit().create(NotificationAPI::class.java)


    val signinAPI: AuthAPI = getRetrofit().create(AuthAPI::class.java)

    val enrollAPI: EnrollmentAPI = getRetrofit().create(EnrollmentAPI::class.java)

    val calendarAPI: CalendarAPI = getRetrofit().create(CalendarAPI::class.java)

    val reviewAPI: ReviewAPI = getRetrofit().create(ReviewAPI::class.java)

    val wateringAPI: WateringAPI = getRetrofit().create(WateringAPI::class.java)


    val authAPI: AuthAPI = getRetrofit().create(AuthAPI::class.java)

    val plantDetailAPI: PlantDetailAPI = getRetrofit().create(PlantDetailAPI::class.java)

    val responsePlantCardData: ResponsePlantCardData =
        getRetrofit().create(ResponsePlantCardData::class.java)

    val deleteAPI: DeleteAPI = getRetrofit().create(DeleteAPI::class.java)


}
