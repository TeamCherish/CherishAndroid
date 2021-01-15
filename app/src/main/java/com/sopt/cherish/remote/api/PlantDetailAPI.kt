package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

data class PlantDetailData(
    @SerializedName("sucess") val success: Boolean,
    @SerializedName("data") val data: Data
)

data class Data(
    @SerializedName("plantResponse") val plantResponse: List<PlantResponse>,
    @SerializedName("plantDetail") val plantDetail: List<PlantDetail>
)

data class PlantDetail(
    @SerializedName("description") val description: String,
    @SerializedName("level_name") val level_name: String,
    @SerializedName("image_url") val image_url: String
)

data class PlantResponse(
    @SerializedName("explanation") val explanation: String,
    @SerializedName("flower_meaning") val flower_meaning: String,
    @SerializedName("image_url") val image_url: String,
    @SerializedName("modifier") val modifier: String,
)

/*
data class PlantDetailData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val plantDetail: List<PlantDetail>,
        val plantResponse: List<PlantResponse>
    ) {
        data class PlantDetail(
            val description: String,
            val image_url: String,
            val level_name: String
        )

        data class PlantResponse(
            val explanation: String,
            val flower_meaning: String,
            val image_url: String,
            val modifier: String
        )
    }
}
*/
interface PlantDetailAPI {
    @GET("plantDetail/{id}")
    fun fetchUserPage(
        @Path("id") plantId: Int
    ): Call<PlantDetailData>
}