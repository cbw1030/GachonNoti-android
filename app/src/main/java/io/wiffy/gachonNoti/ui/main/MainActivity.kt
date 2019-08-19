package io.wiffy.gachonNoti.ui.main

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.model.Component
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : MainContract.View() {

    var menuItem: MenuItem? = null
    lateinit var adapter: PagerAdapter
    var builder: Dialog? = null
    private var backKeyPressedTime: Long = 0L
    lateinit var mPresenter: MainPresenter

    companion object {
        lateinit var mView: MainContract.View
    }

    override fun setTabText(str: String) {
        navigation.getTabAt(0)?.text = str
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "공지사항"

        mView = this
        invisible()

        mPresenter = MainPresenter(this, this@MainActivity)
        mPresenter.initPresent()
    }

    @SuppressLint("ApplySharedPref")
    private fun notificationCheck() {
        if ((!NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled()) and (Component.notificationSet)
        ) AlertDialog.Builder(this).apply {
            setTitle("알림 설정 확인")
            setMessage("가천 알림이의 알림을 허용하시겠습니까?")
            setPositiveButton("OK") { _, _ ->
                val tent = Intent().apply {
                    action = "android.settings.APP_NOTIFICATION_SETTINGS"
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    tent.putExtra("android.provider.extra.APP_PACKAGE", packageName)

                } else {
                    tent.putExtra("app_package", packageName)
                    tent.putExtra("app_uid", applicationInfo.uid)
                }
                startActivity(tent)
                setSharedItem("notiOn", true)
                FirebaseMessaging.getInstance().subscribeToTopic("noti")
            }
            setNegativeButton("Cancel") { _, _ ->
                Component.notificationSet = false
                setSharedItem("notiOn", false)
                FirebaseMessaging.getInstance().unsubscribeFromTopic("noti")
            }
            setCancelable(false)
        }.show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuItem = menu?.add(0, 201735829, 0, "시간표 데이터 삭제")
        menuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = if (item?.itemId == 201735829) {
        mPresenter.resetData()
        true
    } else {
        super.onOptionsItemSelected(item)
    }

    override fun builderUp() {
        if (builder == null) {
            builder = Dialog(this@MainActivity).apply {
                setContentView(R.layout.builder)
                setCancelable(false)
                setCanceledOnTouchOutside(false)
                this.window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
        }
        Handler(Looper.getMainLooper()).post {
            try {
                if (builder?.isShowing == false)
                    builder?.show()
            } catch (e: Exception) {

            }
        }
    }

    override fun builderDismiss() = builder?.let {
        Handler(Looper.getMainLooper()).post {
            try {
                if (builder?.isShowing == true)
                    builder?.dismiss()
            } catch (e: Exception) {

            }
            notificationCheck()
        }
    }

    override fun changeUI(mList: ArrayList<Fragment?>) {
        themeChange()
        Glide.with(this).load(R.drawable.defaults).into(logo_splash2)
        adapter = PagerAdapter(supportFragmentManager, mList)
        navigation.addTab(navigation.newTab().setIcon(R.drawable.tab1_home))
        navigation.addTab(navigation.newTab().setIcon(R.drawable.tab2_id))
        navigation.addTab(navigation.newTab().setIcon(R.drawable.tab3_table))
        navigation.addTab(navigation.newTab().setIcon(R.drawable.tab4_setting))
        pager.adapter = adapter
        pager.offscreenPageLimit = mList.size
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(navigation))
        navigation.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                menuItem?.let {
                    when (tab?.position) {
                        STATE_NOTIFICATION -> {
                            title = "공지사항"
                            it.isVisible = false
                        }
                        STATE_INFORMATION -> {
                            title = "내 정보"
                            it.isVisible = false
                        }
                        STATE_SEARCHER -> {
                            title = "강의실"
                            it.isVisible = true
                        }
                        STATE_SETTING -> {
                            title = "설정"
                            it.isVisible = false
                        }
                        else -> {
                            title = "가천알림이"
                            it.isVisible = false
                        }
                    }
                    Component.state = tab?.position ?: STATE_NOTIFICATION
                    pager.currentItem = tab?.position ?: STATE_NOTIFICATION
                }
            }
        })
    }

    @SuppressLint("ApplySharedPref")
    override fun updatedContents() {
        setSharedItem(Component.version, true)

        AlertDialog.Builder(this@MainActivity).apply {
            setTitle("${Component.version} 버전 업데이트")
            setMessage(" ${resources.getString(R.string.update)}")
            setPositiveButton(
                "OK"
            ) { _, _ -> }
        }.show()
    }

    override fun onBackPressed() {
        when (Component.state) {
            STATE_NOTIFICATION -> {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000L) {
                    backKeyPressedTime = System.currentTimeMillis()
                    toast("종료하시려면 한번 더 눌러주세요.")
                } else {
                    finish()
                }
            }
            STATE_WEB_VIEW -> {
            }
            else -> {
                pager.currentItem = STATE_NOTIFICATION
                Component.state = STATE_NOTIFICATION
            }
        }
    }

    override fun allThemeChange() = mPresenter.changeThemes()

    override fun themeChange() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.statusBarColor = resources.getColor(getThemeColor())
        navigation.setBackgroundColor(resources.getColor(getThemeColor()))
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(getThemeColor())))
        supportActionBar!!.elevation = 0f
    }

    override fun onUserLeaveHint() {
        invisible()
        super.onUserLeaveHint()
    }

    override fun onStart() {
        visible()
        super.onStart()
    }

    override fun onResume() {
        Component.noneVisible = false
        visible()
        super.onResume()
    }

    override fun onPause() {
        Component.looper = false
        invisible()
        super.onPause()
    }

    override fun onStop() {
        invisible()
        super.onStop()
    }

    override fun onAttachedToWindow() {
        visible()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        invisible()
        super.onDetachedFromWindow()
    }

    private fun visible() {
        main_main.visibility = View.VISIBLE
        main_splash.visibility = View.GONE
        main_splash.invalidate()
        main_main.invalidate()
    }

    private fun invisible() {
        if (!Component.noneVisible) {
            main_main.visibility = View.GONE
            main_splash.visibility = View.VISIBLE
            main_splash.invalidate()
            main_main.invalidate()
        }
    }

}