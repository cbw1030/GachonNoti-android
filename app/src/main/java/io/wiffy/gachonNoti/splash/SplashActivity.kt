package io.wiffy.gachonNoti.splash

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.main.MainActivity

class SplashActivity : AppCompatActivity(),SplashContract.View {

    lateinit var mPresenter:SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        mPresenter = SplashPresenter(this)
        mPresenter.initPresent()
    }

    override fun changeUI() {
        mPresenter.move()
    }

    override fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onBackPressed() {
//        finish()
    }
    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }
}
