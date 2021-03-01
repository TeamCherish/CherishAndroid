package com.sopt.cherish.util

object MyKeyStore {
    private const val alarmDataStoreName = "cherishAlarmDataStore"
    private const val alarmKeyName = "cherishAlarm"

    fun provideAlarmKeyName(): String = alarmKeyName

    fun provideAlarmDataStoreName(): String = alarmDataStoreName
}