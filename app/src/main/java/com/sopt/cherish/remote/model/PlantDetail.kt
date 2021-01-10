package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

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