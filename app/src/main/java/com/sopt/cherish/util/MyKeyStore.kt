package com.sopt.cherish.util

object MyKeyStore {
    private const val alarmKeyName = "cherishAlarm"
    private const val encryptedSharedPreferencesName = "encryptedSharedPrefs"
    private const val loginTokenPrefsName = "cherishLoginToken"
    private const val loginUserId = "cherishUserId"
    private const val loginUserPassword = "cherishUserPassword"
    private const val loginUserNickname = "cherishUserNickname"
    private const val isSingleInvokeKey = "singleInvoke"
    private const val FCMTokenKey = "FCMToken"
    private const val NEED_TO_WATERING_NOTIFICATION_ID = 0
    private const val RECALL_REVIEW_NOTIFICATION_ID = 1

    fun provideAlarmKeyName(): String = alarmKeyName

    fun provideEncryptedSharedPrefsName(): String = encryptedSharedPreferencesName

    fun provideLoginTokenPrefsName(): String = loginTokenPrefsName

    fun provideLoginUserId(): String = loginUserId

    fun provideLoginUserPassword(): String = loginUserPassword

    fun provideLoginUserNickname(): String = loginUserNickname

    fun provideNeedToWateringNotificationId(): Int = NEED_TO_WATERING_NOTIFICATION_ID

    fun provideRecallReviewNotificationId(): Int = RECALL_REVIEW_NOTIFICATION_ID

    fun provideSingleInvokeKey(): String = isSingleInvokeKey

    fun provideFCMTokenKey(): String = FCMTokenKey
}