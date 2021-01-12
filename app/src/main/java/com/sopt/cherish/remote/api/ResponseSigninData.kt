package com.sopt.cherish.remote.model

data class ResponseSigninData(
    val data: Data,
    val message: String,
    val success: Boolean
) {
    data class Data(
        val UserId: Int
    )
}