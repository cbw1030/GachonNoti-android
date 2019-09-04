package io.wiffy.gachonNoti.ui.splash

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.function.getWordByKorean
import io.wiffy.gachonNoti.function.setSharedItem
import java.text.SimpleDateFormat
import java.util.*


class SplashPresenter(private val mView: SplashContract.View, private val context: Context) :
    SplashContract.Presenter {

    override fun initPresent() {
        checking()
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
                NotificationChannel(
                    channel,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = ""
                    enableLights(true)
                    enableVibration(true)
                    setShowBadge(false)
                    vibrationPattern = longArrayOf(100, 200, 100, 200)
                }
            notiChannel.createNotificationChannel(channelMessage)
        }
        subscribe()
    }

    private fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnSuccessListener {
            setSharedItem("firstBooting", false)
        }
        mView.moveToMain()
    }

    @SuppressLint("SimpleDateFormat")
    private fun checking() {
        try {
            val date = SimpleDateFormat("MMdd").format(Date())
            val birthday: String? = getSharedItem<String>("birthday").substring(2, 6)
            val gender = getSharedItem("gender", true)
            val name: String? = getSharedItem<String>("name")

            // birthday == date && name != null

            if (birthday == date && name != null) {
                Component.isBirthday = true
                Component.delay = 1500
                mView.setParams()
                mView.setImageView(R.drawable.happybirthday)
                mView.setBirthdayText(
                    "${randomCake(gender)} ${getWordByKorean(
                        name.substring(1, name.length),
                        "아",
                        "야"
                    )}, 생일축하해~!"
                )
            } else {
                if (Component.darkTheme) mView.darkTheme()
                else mView.setImageView(R.drawable.defaults)
            }
        } catch (e: Exception) {
            if (Component.darkTheme) mView.darkTheme()
            else mView.setImageView(R.drawable.defaults)
        }
    }

    private fun randomCake(bool: Boolean) = if (bool) {
        mView.setTextColor(R.color.main2Blue)
        arrayOf("잘생긴", "귀여운", "힘이 센", "핵 인싸", "머리가 좋은", "인기 많은")
    } else {
        mView.setTextColor(R.color.red)
        arrayOf("예쁜", "귀여운", "핵 인싸", "머리가 좋은", "인기 많은", "사랑스러운")
    }.let {
        it[Random().nextInt(it.size)]
    }
}