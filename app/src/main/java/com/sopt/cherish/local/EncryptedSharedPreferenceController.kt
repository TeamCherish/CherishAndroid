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

    companion object {
        private val cherishAlarmDataKey = MyKeyStore.provideAlarmKeyName()
        private val cherishLoginTokenKey = MyKeyStore.provideLoginTokenPrefsName()
        private val cherishLoginUserId = MyKeyStore.provideLoginUserId()
        private val cherishLoginUserPassword = MyKeyStore.provideLoginUserPassword()
        private val cherishLoginUserNickname = MyKeyStore.provideLoginUserNickname()
    }

}