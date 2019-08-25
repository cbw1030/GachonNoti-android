package io.wiffy.gachonNoti.ui.splash

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.*
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

        getScreenSize(this@SplashActivity)

        mPresenter = SplashPresenter(this, applicationContext)
        mPresenter.initPresent()
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

    override fun setBirthdayText(str: String) {
        gachonAlimi.text = str
    }

    override fun setParams() {
        bububu.setBackgroundColor(resources.getColor(R.color.white))
        bababa.setBackgroundColor(resources.getColor(R.color.white))
        logo_splash.layoutParams = logo_splash.layoutParams.apply {
            width = 512
            height = 512
        }
    }

    override fun setTextColor(id: Int) {
        gachonAlimi.setTextColor(resources.getColor(id))
    }

    override fun setImageView(id: Int) {
        Glide.with(this).load(id).into(logo_splash)
    }
}
