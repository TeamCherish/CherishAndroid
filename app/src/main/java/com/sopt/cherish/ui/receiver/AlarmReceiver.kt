package com.sopt.cherish.ui.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // TODO : BACKSTACK에 있는 ACTIVITY가 FOREGROUND로 오게만 하면 PUSH 알림은 끝이 난다!!!
    }
}