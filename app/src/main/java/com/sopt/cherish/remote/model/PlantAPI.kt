package com.sopt.cherish.remote.model

import com.google.gson.annotations.SerializedName

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
    @SerializedName("id") val id: Int,
)

data class ReviseCherishRes(
    val reviseResponse: UtilResponse
)

interface PlantAPI