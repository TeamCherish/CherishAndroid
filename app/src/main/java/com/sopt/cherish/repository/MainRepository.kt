package com.sopt.cherish.repository

import com.sopt.cherish.remote.api.MyPageAPI
import com.sopt.cherish.remote.api.MyPageUserRes
import com.sopt.cherish.remote.api.UserAPI
import com.sopt.cherish.remote.api.UserResult
import com.sopt.cherish.util.SimpleLogger

/**
 * Created on 01-07 by SSong-develop
 */
class MainRepository(
    private val userAPI: UserAPI,
    private val myPageAPI: MyPageAPI
) {
    suspend fun fetchCherishUser(userId: Int): UserResult {
        SimpleLogger.logI(userAPI.getCherishUser(1).toString())
        return userAPI.getCherishUser(userId)
    }

    suspend fun fetchCherishUserPageData(userId: Int): MyPageUserRes {
        return myPageAPI.fetchUserPage(userId)
    }
}