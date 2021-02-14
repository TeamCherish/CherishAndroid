package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName

data class UtilResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)

data class UtilResponseWithOutStatus(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)