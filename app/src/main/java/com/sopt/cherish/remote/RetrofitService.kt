package com.sopt.cherish.remote

import com.sopt.cherish.remote.model.*
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("sample/sample")
    fun test() {

    }

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
        @Body body : RequestSigninData
    ): Call<ResponseSigninData>
}
