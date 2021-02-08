package com.sopt.cherish.remote

import com.sopt.cherish.remote.api.ResponseDetailData
import com.sopt.cherish.remote.model.RequestEnrollData
import com.sopt.cherish.remote.model.RequestSigninData
import com.sopt.cherish.remote.model.ResponseEnrollData
import com.sopt.cherish.remote.model.ResponseSigninData
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {

    @Headers("Content-Type:multipart/application-json")
    @POST("cherish")
    fun postUsers(
        @Body body: RequestEnrollData
    ): Call<ResponseEnrollData>

    @Headers("Content-Type:multipart/application-json")
    @GET("PlantDetail/{id}")
    fun getDetailplant(
        @Query("id") id: Int
    ): Call<ResponseDetailData>


    // 로그인
    @Headers("Content-Type:multipart/application-json")
    @POST("login/signin")
    fun signIn(
        @Body body: RequestSigninData
    ): Call<ResponseSigninData>
}
