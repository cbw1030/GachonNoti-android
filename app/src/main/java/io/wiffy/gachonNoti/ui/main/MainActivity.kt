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
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.*
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.model.PatternLockDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MainContract.View() {

    var menuItem1: MenuItem? = null
    lateinit var adapter: PagerAdapter
    var builder: Dialog? = null
    private var backKeyPressedTime: Long = 0L
    lateinit var mPresenter: MainPresenter

    companion object {
        lateinit var mView: MainContract.View
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Component.setBuilder(this@MainActivity)
        title = Component.titles[STATE_NOTIFICATION].first

        mView = this
        invisible()

        mPresenter = MainPresenter(this)
        mPresenter.initPresent()
    }

    @SuppressLint("ApplySharedPref")
    private fun notificationCheck() {
        if ((!NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled()) and (Component.notificationSet)
        ) MaterialAlertDialogBuilder(this).apply {
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

    override fun setTabText(str: String) {
        navigation.getTabAt(0)?.text = str
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
        else -> super.onOptionsItemSelected(item)
    }

    override fun builderUp() = Handler(Looper.getMainLooper()).post {
        Component.getBuilder()?.show()
    }


    override fun builderDismiss() = Handler(Looper.getMainLooper()).post {
        Component.getBuilder()?.dismiss()
    }

    private fun logoChange(id: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            logo_splash2.setImageDrawable(applicationContext.resources.getDrawable(id))
        } else {
            Glide.with(this).load(id).into(logo_splash2)
        }
    }

    override fun initView(mList: ArrayList<Fragment?>) {
        notificationCheck()
        themeChange()
        if (Component.darkTheme) logoChange(R.drawable.default_dark)
        else logoChange(R.drawable.defaults)

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
        MaterialAlertDialogBuilder(this).apply {
            setMessage("패턴을 설정하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                setPattern()
            }
            setNegativeButton("아니요") { _, _ -> }

        }.show()
        patternVisibility()
    }


    override fun patternVisibility() = mPresenter.patternVisibility()

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
        MaterialAlertDialogBuilder(this).apply {
            setTitle("${Component.version} 버전 업데이트")
            setMessage(R.string.update)
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
        if (Component.darkTheme) darkTheme()
        else {
            window.statusBarColor = resources.getColor(getThemeColor())
            navigation.setBackgroundColor(resources.getColor(getThemeColor()))
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(getThemeColor())))
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        supportActionBar!!.elevation = 0f
    }

    override fun darkTheme() {
        main_splash.setBackgroundColor(resources.getColor(getDarkColor1()))
        window.statusBarColor = resources.getColor(getDarkColor2())
        navigation.setBackgroundColor(resources.getColor(getDarkColor2()))
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(resources.getColor(getDarkColor2())))
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