package com.sopt.cherish.remote.api

data class ResponseDetailData(
    val data: Data,
    val success: Boolean
) {
    data class Data(
        val plantDetail: List<PlantDetail>,
        val plantRes: PlantRes
    ) {
        data class PlantDetail(
            val description: String,
            val image_url: String,
            val level_name: String
        )

        data class PlantRes(
            val explanation: String,
            val flower_meaning: String,
            val image_url: String,
            val modifier: String
        )
    }
}