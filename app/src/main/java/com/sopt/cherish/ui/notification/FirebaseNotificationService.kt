package com.sopt.cherish.ui.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sopt.cherish.R
import com.sopt.cherish.ui.signin.SignInActivity
import com.sopt.cherish.util.SimpleLogger
import com.sopt.cherish.util.extension.ContextExtension.getIntent

class FirebaseNotificationService : FirebaseMessagingService() {

    /**
     * 서버 코드 보니까 api를 실행한 후에 flag = 2 로 값을 변경한다.
     * 근데 이제 문제는 flag=2가 되었을때다 저 flag=2 일떄 리뷰 늦춰졌을때 알림이 오는 푸시알림을 진행 해야 한다는 것이다.
     */
    override fun onNewToken(token: String) {
        SimpleLogger.logI(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val intent = this.getIntent<SignInActivity>()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId = 100

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notification = NotificationCompat.Builder(
            applicationContext,
            this.getString(R.string.notification_cherish_need_to_watering_user_channel_id)
        )
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setSmallIcon(R.drawable.login_logo)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(notificationId, notification)
    }
}