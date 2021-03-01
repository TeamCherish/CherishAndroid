package com.sopt.cherish.local

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.sopt.cherish.util.MyKeyStore

class AlarmController(
    private val alarmDataStore: SharedPreferences
) : OnControlled {

    @SuppressLint("CommitPrefEdits")
    override fun setAlarmKey(alarmKey: Boolean) {
        alarmDataStore.edit().putBoolean(cherishAlarmDataKey, alarmKey).apply()
    }

    override fun getAlarmKey(): Boolean =
        alarmDataStore.getBoolean(cherishAlarmDataKey, false)

    companion object {
        private val cherishAlarmDataKey = MyKeyStore.provideAlarmKeyName()
    }

}