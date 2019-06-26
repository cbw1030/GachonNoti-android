package io.wiffy.gachonNoti.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.get
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.notification.Parse
import io.wiffy.gachonNoti.ui.main.notification.ParseList
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var mPresenter: MainPresenter
    lateinit var adapter: PagerAdapter
    lateinit var builder: Dialog
    var protector: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        Util.state = Util.STATE_NOTIFICATION
        invisible()
        supportActionBar?.hide()
        mPresenter = MainPresenter(this)
        builder = Dialog(this)
        builder.setContentView(R.layout.builder)
        builder.setCancelable(false)
        builder.setCanceledOnTouchOutside(false)
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
        mPresenter.initPresent()
    }

    override fun onStart() {
        visible()
        super.onStart()
    }

    @SuppressLint("ApplySharedPref")
    private fun notiCheck() {
        if (NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled() == false and Util.notifiSet
        ) {

            val builders = AlertDialog.Builder(this,R.style.light_dialog)
            builders.setTitle("알림 설정 확인")
            builders.setMessage("가천 알림이의 알림을 허용하시겠습니까?")
            builders.setPositiveButton("OK") { _, _ ->
                val tent = Intent()
                tent.action = "android.settings.APP_NOTIFICATION_SETTINGS"

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

                } else {
                    tent.putExtra("app_package", packageName)
                    tent.putExtra("app_uid", applicationInfo.uid)
                }
                startActivity(tent)
                finish()
            }
            builders.setNegativeButton("Cancel") { _, _ ->
                Util.notifiSet=false
                Util.sharedPreferences.edit().putBoolean("notiOn", false).commit()
                FirebaseMessaging.getInstance().unsubscribeFromTopic("noti")
                finish()
            }
            builders.setCancelable(false)
            builders.show()
        }
    }

    override fun onResume() {
        visible()
        super.onResume()
    }


    override fun onPause() {
        Util.index = 0
        Util.looper = false
        invisible()
        super.onPause()
    }

    override fun onStop() {
        invisible()
        super.onStop()
    }

    override fun builderUp() {
        builder.show()
    }

    override fun makeToast(str: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show()
        }
    }

    override fun builderDismiss() {
        builder.dismiss()
        notiCheck()
    }

    override fun changeUI(mList: ArrayList<Fragment>) {
        adapter = PagerAdapter(supportFragmentManager, mList)
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Notification)))
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Setting)))
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(navigation))
        navigation.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Util.state = tab?.position?:0
                pager.currentItem = tab?.position?:0
            }
        })

    }

    override fun onBackPressed() {
        when (Util.state) {
            Util.STATE_NOTIFICATION -> {
                finish()
            }
            Util.STATE_SETTING -> {
                pager.currentItem = Util.STATE_NOTIFICATION
                Util.state =Util.STATE_NOTIFICATION
            }
            Util.STATE_WEBVIEW -> {

            }
        }
    }

    override fun onUserLeaveHint() {
        invisible()
        super.onUserLeaveHint()
    }

    private fun visible() {
        main_main.visibility = View.VISIBLE
        main_splash.visibility = View.GONE
        main_splash.invalidate()
        main_main.invalidate()
    }

    override fun onAttachedToWindow() {
        visible()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        invisible()
        super.onDetachedFromWindow()
    }

    private fun invisible() {
        main_main.visibility = View.GONE
        main_splash.visibility = View.VISIBLE
        main_splash.invalidate()
        main_main.invalidate()
    }

}