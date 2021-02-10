package com.sopt.cherish.remote.api

import com.google.gson.annotations.SerializedName
import java.util.*

// Cherish 상세보기
// Param으로 CherishId가 들어감
// 나영이가 만들어놈 지워도 됨
data class DetailCherishRes(
    @SerializedName("nickname") val nickName: String,
    @SerializedName("dDay") val remainWateringDay: Number,
    @SerializedName("birth") val birth: String,
    @SerializedName("plant_name") val plantName: String,
    @SerializedName("plant_thumbnail_image_url") val plantThumbnailImageUrl: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("keyword1") val userStatus1: String,
    @SerializedName("keyword2") val userStatus2: String,
    @SerializedName("keyword3") val userStatus3: String,
    @SerializedName("reviews") val reviews: List<ReviewData>
)

data class ReviewData(
    @SerializedName("id") val id: Int,
    @SerializedName("review") val review: String,
    @SerializedName("water_date") val waterDate: Date,
    @SerializedName("keyword1") val userStatus1: String,
    @SerializedName("keyword2") val userStatus2: String,
    @SerializedName("keyword3") val userStatus3: String,
)

interface PlantAPI