package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("dDay") val dDay: Int,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("growth") val growth: Int,
    @SerializedName("image_url") val userPlantImageUrl: String,
    @SerializedName("thumbnail_image_url") val thumbnailImageUrl: String
)

data class CreateUserRequest

data class EditUserRequest

data class UserResult(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val
)

interface UserAPI