package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

data class UtilResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)