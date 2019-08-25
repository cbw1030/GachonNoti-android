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
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.model.PatternLockDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : MainContract.View() {

    var menuItem1: MenuItem? = null
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
        title = Component.titles[STATE_NOTIFICATION].first

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

                startActivity(Intent().apply {
                    action = "android.settings.APP_NOTIFICATION_SETTINGS"
                }.apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        putExtra("android.provider.extra.APP_PACKAGE", packageName)

                    } else {
                        putExtra("app_package", packageName)
                        putExtra("app_uid", applicationInfo.uid)
                    }
                })
                mPresenter.positiveButton()
            }
            setNegativeButton("Cancel") { _, _ ->
                mPresenter.negativeButton()
            }
            setCancelable(false)
        }.show()
    }


    override fun changeStatusBar(bool: Boolean) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = if (bool) resources.getColor(getThemeColor())
        else resources.getColor(getThemeMyTransColor())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuItem1 = menu?.add(0, 201735829, 0, "강의실 데이터 삭제")
        menuItem1?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        201735829 -> {
            mPresenter.deleteRoomData()
            true
        }
        201735831 -> {
            mPresenter.resetTimeTable()
            true
        }
        else -> super.onOptionsItemSelected(item)
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
        }
    }

    override fun changeUI(mList: ArrayList<Fragment?>) {
        notificationCheck()
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
                Component.state = tab?.position ?: STATE_NOTIFICATION
                pager.currentItem = tab?.position ?: STATE_NOTIFICATION
                setTitle(
                    Pair(
                        Component.titles[tab?.position ?: 4].first,
                        Component.titles[tab?.position ?: 4].second
                    )
                )
            }
        })
    }

    override fun askSetPattern() {
        AlertDialog.Builder(this@MainActivity).apply {
            setMessage("패턴을 설정하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                setPattern()
            }
            setNegativeButton("아니요") { _, _ -> }
        }.create().show()

        patternVisibility()
    }


    override fun patternVisibility() = mPresenter.patternVisibility()

    override fun checkPattern() = mPresenter.checkPattern()

    override fun changePattern() {
        PatternLockDialog(this@MainActivity, CHANGE_PATTERN).apply {
            setOnCancelListener {
                toast("설정을 취소하였습니다.")
            }
        }.show()
    }

    private fun setPattern() {
        PatternLockDialog(this@MainActivity, SET_PATTERN).apply {
            setOnCancelListener {
                toast("패턴은 설정에서 설정할 수 있습니다.")
            }
        }.show()
    }

    override fun setTitle(pair: Pair<String, Boolean>) {
        title = pair.first
        menuItem1?.let {
            it.isVisible = pair.second
        }
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
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
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

    override fun logout() = mPresenter.logout()
    override fun login() = mPresenter.login()
    override fun mainLogout() = mPresenter.mainLogChecking()

    override fun mainLogin() = mPresenter.mainLogChecking()
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