package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName
import java.util.*

// Cherish 등록
data class EnrollCherishReq(
    @SerializedName("name") val name: String,
    @SerializedName("nickname") val nickName: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("cycle_date") val cycleDate: Int,
    @SerializedName("notice_time") val noticeTime: String,
    @SerializedName("UserId") val userId: Int
)

data class EnrollCherishRes(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("explanation") val explanation: String,
    @SerializedName("modifier") val modifier: String,
    @SerializedName("flower_meaning") val flowerMeaning: String,
    @SerializedName("PlantStatusId") val plantStatusId: Int,
    @SerializedName("image_url") val imageUrl: String
)

data class EnrollCherishResult(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val plant: EnrollCherishRes
)

// Cherish 수정
data class ReviseCherishReq(
    @SerializedName("nickname") val nickName: String,
    @SerializedName("birth") val birth: String,
    @SerializedName("cycle_date") val cycleDate: Int,
    @SerializedName("notice_time") val noticeTime: String,
    @SerializedName("water_notice") val waterNotice: Boolean,
    @SerializedName("id") val id: Int
)

data class ReviseCherishRes(
    val reviseResponse: UtilResponse
)

// Cherish 상세보기
// Param으로 CherishId가 들어감
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