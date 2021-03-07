package com.sopt.cherish.util

object MyKeyStore {
    private const val alarmKeyName = "cherishAlarm"
    private const val encryptedSharedPreferencesName = "encryptedSharedPrefs"
    private const val loginTokenPrefsName = "cherishLoginToken"

    fun provideAlarmKeyName(): String = alarmKeyName

    fun provideEncryptedSharedPrefsName(): String = encryptedSharedPreferencesName

    fun provideLoginTokenPrefsName(): String = loginTokenPrefsName
}