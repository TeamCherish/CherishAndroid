package com.sopt.cherish.util

object MyKeyStore {
    private const val alarmDataStoreName = "cherishAlarmDataStore"
    private const val alarmKeyName = "cherishAlarm"
    private const val encryptedSharedPreferencesName = "encryptedSharedPrefs"

    fun provideAlarmKeyName(): String = alarmKeyName

    fun provideAlarmDataStoreName(): String = alarmDataStoreName

    fun provideEncryptedSharedPrefsName(): String = encryptedSharedPreferencesName
}