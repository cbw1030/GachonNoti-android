package io.wiffy.gachonNoti.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.Util.Companion.setSharedItem
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity(), SplashContract.View {

    lateinit var mPresenter: SplashPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = when (Util.theme) {
            "red" -> resources.getColor(R.color.deepRed)
            "green" -> resources.getColor(R.color.deepGreen)
            else -> resources.getColor(R.color.main2DeepBlue)
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Glide.with(this).load(R.drawable.defaults).into(logo_splash)
        mPresenter = SplashPresenter(this, applicationContext)
        mPresenter.initPresent()
    }

    override fun changeUI() = mPresenter.move()

    @SuppressLint("ApplySharedPref")
    override fun subscribe() {
        FirebaseMessaging.getInstance().subscribeToTopic("noti").addOnSuccessListener {
            setSharedItem("firstBooting",false)
            Log.d("asdf", "noti success")
        }
        changeUI()
    }

    override fun moveToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.abc_fade_in, R.anim.not_move_activity)
        finish()
    }

    override fun onBackPressed() = finish()


    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }

}
