package com.sopt.cherish.remote.model

data class ResponseDetailData(
    val data: List<Data>,
    val success: Boolean
) {
    data class Data(
        val description: String,
        val image_url: String,
        val level: Int
    )
}