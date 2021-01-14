package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

// request param id
data class DetailPlantRes(
    @SerializedName("success") val success: Boolean,
    @SerializedName("data") val detailPlantData: List<DetailPlantData>
)

data class DetailPlantData(
    @SerializedName("level") val level: Int,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String,
)

interface PlantDetailAPI {

    @GET("/plantDetail/{id}")
    @Headers("Content-Type:application/json")
    fun Detailcherish(
        @Path("id") id: Int
    ): Call<ResponseDetailData>
}