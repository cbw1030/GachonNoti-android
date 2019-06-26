package io.wiffy.gachonNoti.ui.splash

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity

class SplashActivity : AppCompatActivity(), SplashContract.View {

    lateinit var mPresenter: SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        mPresenter = SplashPresenter(this,applicationContext)
        mPresenter.initPresent()
    }

    override fun changeUI() {
        mPresenter.move()
    }


    override fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti")
        changeUI()
    }

    override fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.abc_fade_in, R.anim.not_move_activity)
        finish()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }

}
