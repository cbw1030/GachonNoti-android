package io.wiffy.gachonNoti.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.func.getThemeColor
import io.wiffy.gachonNoti.func.getWordByKorean
import io.wiffy.gachonNoti.func.setSharedItem
import io.wiffy.gachonNoti.model.Component
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.text.SimpleDateFormat
import java.util.*


class SplashActivity : SplashContract.View() {

    lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = resources.getColor(getThemeColor())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()

        checking()
        mPresenter = SplashPresenter(this, applicationContext)
        mPresenter.initPresent()
    }

    @SuppressLint("ApplySharedPref")
    override fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnSuccessListener {
            setSharedItem("firstBooting", false)
        }
        moveToMain()
    }

    override fun moveToMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.not_move_activity)
            finish()
        }, Component.delay)
    }

    override fun onBackPressed() = finish()


    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun checking() {
        try {
            val date = SimpleDateFormat("MMdd").format(Date())
            val birthday: String? = getSharedItem<String>("birthday").substring(2, 6)
            val gender = getSharedItem("gender", true)
            val name: String? = getSharedItem<String>("name")

            // birthday == date && name != null
            console("birthday = $birthday / today = $date")

            if (birthday == date && name != null) {
                Component.delay = 1500
                bububu.setBackgroundColor(resources.getColor(R.color.white))
                bababa.setBackgroundColor(resources.getColor(R.color.white))
                logo_splash.layoutParams = logo_splash.layoutParams.apply {
                    width = 512
                    height = 512
                }
                Glide.with(this).load(R.drawable.happybirthday).into(logo_splash)
                gachonAlimi.text =
                    "${randomCake(gender)} ${getWordByKorean(name.substring(1, name.length), "아", "야")}, 생일축하해~!"
            } else {
                Glide.with(this).load(R.drawable.defaults).into(logo_splash)
            }
        } catch (e: Exception) {
            Glide.with(this).load(R.drawable.defaults).into(logo_splash)
        }
    }

    private fun randomCake(bool: Boolean) = if (bool) {
        gachonAlimi.setTextColor(resources.getColor(R.color.main2Blue))
        arrayOf("잘생긴", "귀여운", "힘이 센", "핵 인싸", "머리가 좋은", "인기 많은")
    } else {
        gachonAlimi.setTextColor(resources.getColor(R.color.red))
        arrayOf("예쁜", "귀여운", "핵 인싸", "머리가 좋은", "인기 많은", "사랑스러운")
    }.let {
        it[Random().nextInt(it.size)]
    }
}
