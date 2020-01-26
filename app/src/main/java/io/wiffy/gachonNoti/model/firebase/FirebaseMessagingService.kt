package io.wiffy.gachonNoti.model.firebase

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.skydoves.whatif.whatIfNotNull
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.splash.SplashActivity

class FirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        val prefer = getSharedPreferences("GACHONNOTICE", Context.MODE_PRIVATE)
        if (prefer.getBoolean("notiKey", false)) {
            if (hasKeyword(prefer, p0.data["body"])) {
                //sendNotification(p0)
                sendData(p0)
            }
        } else {
            sendData(p0)
            //sendNotification(p0)
        }
    }

    private fun sendData(p0: RemoteMessage) {
        val title = p0.data["title"] ?: getString(R.string.app_name)
        val message = p0.data["body"] ?: ""
        val clickAction = p0.data["clickAction"] ?: ""

        val isLatest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("keyData", message)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT
        )

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
                .setContentIntent(pendingIntent)

        if (isLatest) {
            notificationBuilder.setChannelId(getString(R.string.channel))
        }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(9999, notificationBuilder.build())

    }


    private fun hasKeyword(prefer: SharedPreferences, data: String?): Boolean {
        if (data == null) return false

        var flag = false

        prefer.getStringSet("notiKeySet", null).whatIfNotNull {
            for (str in it.iterator()) {
                if (data.contains(str)) {
                    flag = true
                    break
                }
            }
        }

        return flag
    }

}