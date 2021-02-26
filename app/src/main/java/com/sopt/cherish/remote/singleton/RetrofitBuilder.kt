package com.sopt.cherish.remote.singleton

import com.sopt.cherish.remote.RetrofitService
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.model.EnrollmentAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BaseUrl = "http://cherishserver.com/"

    private fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
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

    val modifyAPI: ModifyAPI = getRetrofit().create(ModifyAPI::class.java)

    val userinfoAPI:UserInfoAPI= getRetrofit().create(UserInfoAPI::class.java)

    val checkphoneAPI: CheckPhoneAPI= getRetrofit().create(CheckPhoneAPI::class.java)


    val nicknameChangeAPI:NicknameChangeAPI= getRetrofit().create(NicknameChangeAPI::class.java)

    val signUpEmailAPI:SignUpEmailAPI= getRetrofit().create(SignUpEmailAPI::class.java)

}
