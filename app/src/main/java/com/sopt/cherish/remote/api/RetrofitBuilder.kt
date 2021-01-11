package com.sopt.cherish.remote.api

import com.sopt.cherish.remote.RetrofitService
import com.sopt.cherish.remote.model.UserAPI
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
}
