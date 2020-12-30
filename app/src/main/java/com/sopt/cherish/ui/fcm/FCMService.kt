package com.sopt.cherish.ui.fcm

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Firebase push 알림을 위한 서비스 객체
 * FCM
 */

class FCMService : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}