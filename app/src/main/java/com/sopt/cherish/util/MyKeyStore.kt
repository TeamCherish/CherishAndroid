package com.sopt.cherish.util

object MyKeyStore {
    private const val alarmKeyName = "cherishAlarm"
    private const val encryptedSharedPreferencesName = "encryptedSharedPrefs"
    private const val loginTokenPrefsName = "cherishLoginToken"
    private const val loginUserId = "cherishUserId"
    private const val loginUserPassword = "cherishUserPassword"
    private const val loginUserNickname = "cherishUserNickname"

    fun provideAlarmKeyName(): String = alarmKeyName

    fun provideEncryptedSharedPrefsName(): String = encryptedSharedPreferencesName

    fun provideLoginTokenPrefsName(): String = loginTokenPrefsName

    fun provideLoginUserId(): String = loginUserId

    fun provideLoginUserPassword(): String = loginUserPassword

    fun provideLoginUserNickname(): String = loginUserNickname

}