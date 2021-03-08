package com.sopt.cherish.local

interface OnControlled {
    fun setAlarmKey(alarmKey: Boolean)

    fun getAlarmKey(): Boolean

    fun setToken(token: String)

    fun getToken(): String?

    // auto Login
    fun setUserId(userId: Int)

    fun getUserId(): Int?

    fun setUserPassword(userPassword: String)

    fun getUserPassword(): String?

    fun setUserNickname(userNickname: String)

    fun getUserNickname(): String?
}