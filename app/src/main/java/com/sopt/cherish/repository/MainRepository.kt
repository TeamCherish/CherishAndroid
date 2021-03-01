package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.UserAPI
import com.sopt.cherish.remote.api.UserResult

/**
 * Created on 01-07 by SSong-develop
 */
class MainRepository(
    private val userAPI: UserAPI,
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        return userAPI.getCherishUser(userId)
    }
}