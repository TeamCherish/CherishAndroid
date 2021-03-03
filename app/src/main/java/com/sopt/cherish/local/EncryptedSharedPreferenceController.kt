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
        dataStore.getBoolean(cherishAlarmDataKey, false)


    override fun setToken(token: String) {
        dataStore.edit().putString(cherishLoginTokenKey, token).apply()
    }

    override fun getToken(): String? = dataStore.getString(cherishLoginTokenKey, null)

    companion object {
        private val cherishAlarmDataKey = MyKeyStore.provideAlarmKeyName()
        private val cherishLoginTokenKey = MyKeyStore.provideLoginTokenPrefsName()
    }

}