package io.wiffy.gachonNoti.ui.splash

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.extension.getWordByKorean
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.`object`.Component
import io.wiffy.gachonNoti.utils.getSharedItem
import io.wiffy.gachonNoti.utils.setSharedItem
import java.text.SimpleDateFormat
import java.util.*


class SplashPresenter(private val mView: SplashContract.View, private val context: Context) :
    SplashContract.Presenter {

    override fun initPresent() {
        checking()
        if (Component.isLogin && !getSharedItem("mynum", false)) {
            FirebaseMessaging.getInstance().subscribeToTopic(getSharedItem("number"))
                .addOnCompleteListener {
                    setSharedItem("mynum", true)
                }
        }

        if (Component.isNew) {
            manageSubscribe()
        }

        when (Component.firstBoot) {
            true -> channelSet()
            false -> mView.moveToMain()
        }
    }

    private fun manageSubscribe() {
        val pre = getSharedItem("preVersion", "2.0.6").split(".")

        try {
            val a = pre[0].toInt()
            val b = pre[1].toInt()
            val c = pre[2].toInt()

            if ((a < 2) || (a == 2 && b == 0 && c < 6)) {
                newSubscribe()
            }

        } catch (e: java.lang.Exception) {

        }
    }

    private fun newSubscribe() {
        FirebaseMessaging.getInstance().unsubscribeFromTopic("noti")
        FirebaseMessaging.getInstance().subscribeToTopic("noti_android")
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

    @SuppressLint("SimpleDateFormat")
    private fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti_android").addOnSuccessListener {
            setSharedItem("firstBooting", false)
        }
        setSharedItem(
            "lastDate",
            SimpleDateFormat("yyyy-mm-dd").format(Date(System.currentTimeMillis()))
        )
        mView.moveToMain()
    }

    @SuppressLint("SimpleDateFormat")
    private fun checking() {
        val mp = MediaPlayer.create(context, R.raw.ms)
        try {
            val date = SimpleDateFormat("MMdd").format(Date())
            val birthday: String? = getSharedItem<String>("birthday").substring(2, 6)
            val gender = getSharedItem("gender", true)
            val name: String? = getSharedItem<String>("name")

            // birthday == date && name != null
            if (name != null && birthday == date) {
                mp.start()

                Component.isBirthday = true
                Component.delay = 1500
                mView.setParams()
                mView.setImageView(R.drawable.happybirthday)
                mView.setBirthdayText(
                    "${randomCake(gender)} ${getWordByKoreanDo(name, birthday!!)} 생일축하해~!"
                )
            } else {
                if (Component.darkTheme) {
                    mView.darkTheme()
                } else {
                    mView.setImageView(R.drawable.defaults_round)
                }
            }
        } catch (e: Exception) {
            if (Component.darkTheme) {
                mView.darkTheme()
            } else {
                mView.setImageView(R.drawable.defaults_round)
            }
        }
    }

    private fun getWordByKoreanDo(name: String, birth: String): String {
        return if (arrayListOf("0210").contains(birth)) {
            getWordByKorean(
                name.substring(1, name.length),
                "이도",
                "도"
            )
        } else {
            "${getWordByKorean(
                name.substring(1, name.length),
                "아",
                "야"
            )},"
        }
    }


    private fun randomCake(bool: Boolean) = if (bool) {
        mView.setTextColor(R.color.main2Blue)
        arrayOf("잘생긴", "귀여운", "힘이 센", "핵 인싸", "머리가 좋은", "인기 많은", "꽃미남", "밥 잘먹는", "롤 잘하는")
    } else {
        mView.setTextColor(R.color.red)
        arrayOf("예쁜", "귀여운", "핵 인싸", "머리가 좋은", "인기 많은", "사랑스러운", "소중한", "밥 잘먹는", "미래가 밝은")
    }.let {
        it[Random().nextInt(it.size)]
    }
}