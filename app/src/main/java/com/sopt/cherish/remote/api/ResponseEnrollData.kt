package com.sopt.cherish.remote.model


data class ResponseEnrollData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val plant: Plant
    ) {
        data class Plant(
            val PlantStatusId: Int,
            val explanation: String,
            val flower_meaning: String,
            val id: Int,
            val image_url: String,
            val modifier: String,
            val name: String,
            val thumbnail_image_url: String
        )
    }
}