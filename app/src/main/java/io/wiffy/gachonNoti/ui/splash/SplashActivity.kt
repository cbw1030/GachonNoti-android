package io.wiffy.gachonNoti.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getThemeColor
import io.wiffy.gachonNoti.func.setSharedItem
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : SplashContract.View() {

    lateinit var mPresenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.statusBarColor = resources.getColor(getThemeColor())
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()

        Glide.with(this).load(R.drawable.defaults).into(logo_splash)
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
        Handler(Looper.getMainLooper()).post {
            startActivity(Intent(this, MainActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.not_move_activity)
            finish()
        }
    }

    override fun onBackPressed() = finish()


    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }

}
