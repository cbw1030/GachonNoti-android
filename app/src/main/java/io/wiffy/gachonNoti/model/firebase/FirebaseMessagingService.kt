package io.wiffy.gachonNoti.model.firebase

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import io.wiffy.gachonNoti.R

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        if (p0?.notification != null) sendNotification(p0)
    }

    private fun sendNotification(p0: RemoteMessage) {
        val title = p0.notification?.title ?: getString(R.string.app_name)
        val message = p0.notification?.body ?: ""
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.channel))
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notiicon))
                    .setSmallIcon(R.drawable.defaults)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setChannelId(getString(R.string.channel))
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(9999, notificationBuilder.build())
            }
            else -> {
                val notificationBuilder = NotificationCompat.Builder(this, "")
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notiicon))
                    .setSmallIcon(R.drawable.defaults)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(9999, notificationBuilder.build())
            }
        }
    }

}