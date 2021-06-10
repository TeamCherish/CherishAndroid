package com.sopt.cherish.remote.singleton

import com.sopt.cherish.di.Injection
import com.sopt.cherish.remote.api.*
import com.sopt.cherish.remote.model.EnrollmentAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BaseUrl = "http://cherishserver.com/"

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(Injection.provideOkHttpClient())
            .build()
    }

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

    val userinfoAPI: UserInfoAPI = getRetrofit().create(UserInfoAPI::class.java)

    val checkphoneAPI: CheckPhoneAPI = getRetrofit().create(CheckPhoneAPI::class.java)

    val nicknameChangeAPI: NicknameChangeAPI = getRetrofit().create(NicknameChangeAPI::class.java)

    val signUpEmailAPI: SignUpEmailAPI = getRetrofit().create(SignUpEmailAPI::class.java)

    val phoneAuthAPI: PhoneAuthAPI = getRetrofit().create(PhoneAuthAPI::class.java)

    val signUpAPI: SignUpAPI = getRetrofit().create(SignUpAPI::class.java)

    val pwFindingAPI: PwFindingAPI = getRetrofit().create(PwFindingAPI::class.java)

    val updatePasswordAPI: UpdatePasswordAPI = getRetrofit().create(UpdatePasswordAPI::class.java)

    val userDeleteAPI: UserDeleteAPI = getRetrofit().create(UserDeleteAPI::class.java)
}
