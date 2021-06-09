package com.sopt.cherish.local

import android.content.SharedPreferences
import com.sopt.cherish.util.MyKeyStore

class EncryptedSharedPreferenceController(
    private val dataStore: SharedPreferences
) : OnControlled {
    override fun setAlarmKey(alarmKey: Boolean) {
        dataStore.edit().putBoolean(cherishAlarmDataKey, alarmKey).apply()
    }

    override fun getAlarmKey(): Boolean =
        dataStore.getBoolean(cherishAlarmDataKey, true)


    override fun setToken(token: String) {
        dataStore.edit().putString(cherishLoginTokenKey, token).apply()
    }

    override fun getToken(): String? = dataStore.getString(cherishLoginTokenKey, null)

    override fun setUserId(userId: Int) {
        dataStore.edit().putInt(cherishLoginUserId, userId).apply()
    }

    override fun getUserId(): Int? = dataStore.getInt(cherishLoginUserId, -1)

    override fun setUserPassword(userPassword: String) {
        dataStore.edit().putString(cherishLoginUserPassword, userPassword).apply()
    }

    override fun getUserPassword(): String? = dataStore.getString(cherishLoginUserPassword, null)

    override fun setUserNickname(userNickname: String) {
        dataStore.edit().putString(cherishLoginUserNickname, userNickname).apply()
    }

    override fun getUserNickname(): String? = dataStore.getString(cherishLoginUserNickname, null)

    override fun deleteUserId() {
        dataStore.edit().remove(cherishLoginUserId).apply()
    }

    override fun deleteUserPassword() {
        dataStore.edit().remove(cherishLoginUserPassword).apply()
    }

    override fun deleteToken() {
        dataStore.edit().remove(cherishLoginTokenKey).apply()
    }

    override fun isSingleInvoke(): Boolean = dataStore.getBoolean(cherishSingleInvoked, false)


    override fun singleInvoked() {
        dataStore.edit().putBoolean(cherishSingleInvoked, true).apply()
    }

    override fun setFCMToken(token: String) {
        dataStore.edit().putString(cherishFCMToken, token).apply()
    }

    override fun getFCMToken(): String? = dataStore.getString(cherishFCMToken, null)

    companion object {
        private val cherishAlarmDataKey = MyKeyStore.provideAlarmKeyName()
        private val cherishLoginTokenKey = MyKeyStore.provideLoginTokenPrefsName()
        private val cherishLoginUserId = MyKeyStore.provideLoginUserId()
        private val cherishLoginUserPassword = MyKeyStore.provideLoginUserPassword()
        private val cherishLoginUserNickname = MyKeyStore.provideLoginUserNickname()
        private val cherishSingleInvoked = MyKeyStore.provideSingleInvokeKey()
        private val cherishFCMToken = MyKeyStore.provideFCMTokenKey()
    }

}