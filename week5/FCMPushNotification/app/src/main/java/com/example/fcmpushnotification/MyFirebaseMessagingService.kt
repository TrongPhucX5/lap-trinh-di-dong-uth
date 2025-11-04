package com.example.fcmpushnotification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.fcmpushnotification"
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title: String
        val body: String

        // Ưu tiên đọc từ "data" payload
        if (remoteMessage.data.isNotEmpty()) {
            title = remoteMessage.data["title"] ?: "Data Title"
            body = remoteMessage.data["body"] ?: "Data Body"
        }
        // Nếu không có "data", đọc từ "notification" (dành cho lúc "Test")
        else if (remoteMessage.notification != null) {
            title = remoteMessage.notification!!.title!!
            body = remoteMessage.notification!!.body!!
        }
        // Không có gì thì thoát
        else {
            return
        }

        // SỬA 1: Gửi nguyên cả 'remoteMessage'
        generateNotification(title, body, remoteMessage)
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title :  String,  message: String): RemoteViews{
        val remoteView = RemoteViews("com.example.fcmpushnotification", R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)

        // Dùng ảnh từ thư mục 'drawable' (như file của bạn)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.img)

        return remoteView
    }

    // SỬA 2: Thêm tham số 'remoteMessage: RemoteMessage'
    fun generateNotification(title: String, message: String, remoteMessage: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // ✨ THÊM DÒNG NÀY ĐỂ TRACKING CLICK ✨
        remoteMessage.toIntent().extras?.let { intent.putExtras(it) }

        // Thêm FLAG_IMMUTABLE
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            // Dùng icon app (ở trong mipmap) cho icon nhỏ
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce((true))
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(channelId, channelName,
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())
    }
}