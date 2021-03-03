package com.sopt.cherish.local

interface OnControlled {
    fun setAlarmKey(alarmKey: Boolean)

    fun getAlarmKey(): Boolean

    fun setToken(token: String)

    fun getToken(): String?
}