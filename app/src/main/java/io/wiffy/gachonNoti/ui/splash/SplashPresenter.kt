package io.wiffy.gachonNoti.ui.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util


class SplashPresenter(private val mView: SplashContract.View, private val context: Context) : SplashContract.Presenter {

    override fun initPresent() = when (Util.firstBoot) {
        true -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = context.getString(R.string.channel)
                val channelName = context.getString(R.string.app_name)
                val notiChannel =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val channelMessage = NotificationChannel(channel, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                channelMessage.description = ""
                channelMessage.enableLights(true)
                channelMessage.enableVibration(true)
                channelMessage.setShowBadge(false)
                channelMessage.vibrationPattern = addList()
                notiChannel.createNotificationChannel(channelMessage)

            }
            mView.subscribe()
        }
        false -> mView.changeUI()
    }

    private fun addList(): LongArray {
        val myList = LongArray(4)
        myList[0] = 100
        myList[1] = 200
        myList[2] = 100
        myList[3] = 200
        return myList
    }

    override fun move() {
        Handler(Looper.getMainLooper()).postDelayed({
            mView.moveToMain()
        }, 1234)
    }
}