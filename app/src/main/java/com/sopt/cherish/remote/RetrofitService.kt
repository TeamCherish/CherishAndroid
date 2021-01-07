package com.sopt.cherish.remote

import retrofit2.http.GET

interface RetrofitService {
    @GET("sample/sample")
    fun test() {

    }
}