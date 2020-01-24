package io.wiffy.gachonNoti.model.firebase

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skydoves.whatif.whatIfNotNull
import io.wiffy.gachonNoti.R

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (p0.notification != null) {
            val prefer = getSharedPreferences("GACHONNOTICE", Context.MODE_PRIVATE)
            if (prefer.getBoolean("notiKey", false) && hasKeyword(prefer, p0.notification?.body)) {
                sendNotification(p0)
            } else {
                sendNotification(p0)
            }
        }
    }

    private fun sendNotification(p0: RemoteMessage) {
        val title = p0.notification?.title ?: getString(R.string.app_name)
        val message = p0.notification?.body ?: ""

        val isLatest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        val notificationBuilder =
            NotificationCompat.Builder(
                this, if (isLatest) {
                    getString(R.string.channel)
                } else {
                    ""
                }
            )
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.defaults))
                .setSmallIcon(R.drawable.notiicon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

        if (isLatest) {
            notificationBuilder.setChannelId(getString(R.string.channel))
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(9999, notificationBuilder.build())
    }

    private fun hasKeyword(prefer:SharedPreferences, data:String?):Boolean
    {
        if(data==null)return false

        var flag = false

        prefer.getStringSet("notiKeySet", null).whatIfNotNull {
           for(str in it.iterator())
           {
               if(data.contains(str)){
                   flag = true
                   break
               }
           }
        }

        return flag
    }

}