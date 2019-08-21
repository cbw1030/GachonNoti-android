package io.wiffy.gachonNoti.ui.splash

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Component


class SplashPresenter(private val mView: SplashContract.View, private val context: Context) : SplashContract.Presenter {

    override fun initPresent() {
        when (Component.firstBoot) {
            true -> channelSet()
            false -> mView.moveToMain()
        }
    }

    private fun channelSet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = context.getString(R.string.channel)
            val channelName = context.getString(R.string.app_name)
            val notiChannel =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelMessage =
                NotificationChannel(channel, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = ""
                    enableLights(true)
                    enableVibration(true)
                    setShowBadge(false)
                    vibrationPattern = longArrayOf(100, 200, 100, 200)
                }
            notiChannel.createNotificationChannel(channelMessage)
        }
        mView.subscribe()
    }

}