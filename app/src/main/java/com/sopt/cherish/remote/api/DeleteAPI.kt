package com.sopt.cherish.remote.api

import retrofit2.Call
import retrofit2.http.*


data class ResponseDeleteData(

    val success: Boolean,
    val message: String
)


interface DeleteAPI {
    @Headers("Content-Type:application/json")
    @DELETE("cherish/:{id}")
    fun plantdelete(
        @Path("id") cherishid: Int
    ): Call<ResponseDeleteData>
}