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

    override fun initPresent() {
        when (Util.firstBoot) {
            true -> {
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
                            vibrationPattern = addList()
                        }
                    notiChannel.createNotificationChannel(channelMessage)
                }
                mView.subscribe()
            }
            false -> mView.changeUI()
        }
    }

    private fun addList() = LongArray(4).apply {
        this[0] = 100
        this[1] = 200
        this[2] = 100
        this[3] = 200
    }

    override fun move() = Handler(Looper.getMainLooper()).post { mView.moveToMain() }

}