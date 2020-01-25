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
            if (hasKeyword(prefer, p0.data["body"]))
            {
                //sendNotification(p0)
                sendData(p0)
            }
        } else {
            sendData(p0)
            //sendNotification(p0)
        }
    }

//    Notification이 아닌 Data로 보내야만 catch하여 설정가능 -> Intent도 사용 가능하더라.
//    # fcm 푸시 메세지 요청 주소
//    url = 'https://fcm.googleapis.com/fcm/send'
//    token = "AAAAx5GXkQE:APA91bHFo3VHbWdQxGVL6lomgIvYnFBcizA1BrmrsTbfFO7_DR31uFjGBesMBsrlOLkPNH1azvXEoZw4an_GuEtovxBI3YjBA-VKhKmy7P5FRpM4FGPWnlyfZ-5CXX9quTQWJG2Rsvun"
//
//    # 인증 정보(서버 키)를 헤더에 담아 전달
//    headers = {
//        'Authorization': 'key='+token,
//        'Content-Type': 'application/json; UTF-8',
//    }
//
//    # 보낼 내용과 대상을 지정
//    content = {
//        'to': "/topics/noti_android",
//        'data': {
//            'title': '가천알림이',
//            'body': strf,
//            'clickAction': 'android.intent.action.MAIN'
//        }
//    }
//
//    # json 파싱 후 requests 모듈로 FCM 서버에 요청
//    requests.post(url, data=json.dumps(content), headers=headers)

    private fun sendData(p0:RemoteMessage)
    {
        val title = p0.data["title"] ?: getString(R.string.app_name)
        val message = p0.data["body"] ?: ""
        val clickAction =p0.data["clickAction"]?:""

        val isLatest = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

        val intent = Intent(this, SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("keyData",message)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT
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