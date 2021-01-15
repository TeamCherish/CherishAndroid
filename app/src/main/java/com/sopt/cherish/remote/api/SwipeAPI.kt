package com.sopt.cherish.remote.api

data class SwipeAPI(
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