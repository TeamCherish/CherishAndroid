package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.UserAPI
import com.sopt.cherish.remote.api.UserResult

/**
 * Created on 01-07 by SSong-develop
 * todo : 서버 열리는 대로 suspend 대신에 전부 enqueue로 바꿀거야
 */
class MainRepository(
    private val userAPI: UserAPI,
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        return userAPI.getCherishUser(userId)
    }
}