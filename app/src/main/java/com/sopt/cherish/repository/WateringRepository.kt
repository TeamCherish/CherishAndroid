package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.PostponeWateringDateReq
import com.sopt.cherish.remote.api.WateringAPI

class WateringRepository(
    private val wateringAPI: WateringAPI
) {
    suspend fun postponeWateringDate(postponeWateringDateReq: PostponeWateringDateReq) =
        wateringAPI.postponeWateringDate(postponeWateringDateReq)
}