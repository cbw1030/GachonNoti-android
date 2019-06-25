package io.wiffy.gachonNoti.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
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
                pager.currentItem = tab?.position!!
            }
        })

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

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (event?.action == KeyEvent.ACTION_DOWN)
            when (event.keyCode) {
                KeyEvent.KEYCODE_MENU -> {
                    pager.currentItem = 1
                    navigation[1].isSelected = true
                    return true
                }
            }
        return true
    }
}