package com.sopt.cherish.remote.api

data class ResponseCardData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val birth: String,
        val dDay: Int,
        val duration: Int,
        val gage: Double,
        val keyword1: String,
        val keyword2: String,
        val keyword3: String,
        val name: String,
        val nickname: String,
        val phone: String,
        val plantId: Int,
        val plant_name: String,
        val plant_thumbnail_image_url: String,
        val reviews: List<Any>,
        val status: String,
        val status_message: String
    )
}