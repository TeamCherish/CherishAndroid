package com.sopt.cherish.repository

import com.sopt.cherish.remote.model.UserAPI
import com.sopt.cherish.remote.model.UserResult
import com.sopt.cherish.util.SimpleLogger

/**
 * Created on 01-07 by SSong-develop
 */
class MainRepository(
    private val userAPI: UserAPI
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        SimpleLogger.logI(userAPI.getCherishUser(1).toString())
        return userAPI.getCherishUser(userId)
    }
}