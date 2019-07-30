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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.firebase.messaging.FirebaseMessaging
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity(), MainContract.View {
    var menuItem: MenuItem? = null
    lateinit var adapter: PagerAdapter
    var builder: Dialog? = null
    var backKeyPressedTime: Long = 0L
    lateinit var mPresenter: MainPresenter

    companion object {
        lateinit var mView: MainContract.View

    }

    override fun setTabText(str: String) {
        navigation.getTabAt(0)?.text = str
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        mView = this
        setContentView(R.layout.activity_main)
        Util.state = Util.STATE_NOTIFICATION
        invisible()
        mPresenter = MainPresenter(this, this@MainActivity)
        mPresenter.initPresent()
    }

    override fun onStart() {
        visible()
        super.onStart()
    }

    override fun onResume() {
        Util.novisible = false
        visible()
        super.onResume()
    }


    override fun onPause() {
        Util.looper = false
        invisible()
        super.onPause()
    }

    override fun onStop() {
        invisible()
        super.onStop()
    }

    @SuppressLint("ApplySharedPref")
    private fun notiCheck() {
        if ((!NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled()) and (Util.notificationSet)
        ) {

            val builders = AlertDialog.Builder(this)
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
                Util.sharedPreferences.edit().putBoolean("notiOn", true).commit()
                FirebaseMessaging.getInstance().subscribeToTopic("noti")
            }
            builders.setNegativeButton("Cancel") { _, _ ->
                Util.notificationSet = false
                Util.sharedPreferences.edit().putBoolean("notiOn", false).commit()
                FirebaseMessaging.getInstance().unsubscribeFromTopic("noti")
            }
            builders.setCancelable(false)
            builders.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuItem = menu?.add(0, 201735829, 0, "시간표 데이터 삭제")
        menuItem?.isVisible = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 201735829) {
            mPresenter.resetData()
            return true
        }
        return super.onOptionsItemSelected(item)
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

    override fun makeToast(str: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT).show()
        }
    }

    override fun builderDismiss() {
        if (builder != null) {
            Handler(Looper.getMainLooper()).post {
                try {
                    if (builder?.isShowing == true)
                        builder?.dismiss()
                } catch (e: Exception) {

                }
                notiCheck()
            }
        }

    }

    fun themeChange() {
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                when (Util.theme) {
                    "red" -> {
                        window.statusBarColor = resources.getColor(R.color.red)
                        navigation.setBackgroundColor(resources.getColor(R.color.deepRed))
                        resources.getColor(R.color.red)
                    }
                    "green" -> {
                        window.statusBarColor = resources.getColor(R.color.green)
                        navigation.setBackgroundColor(resources.getColor(R.color.deepGreen))
                        resources.getColor(R.color.green)
                    }
                    else -> {
                        window.statusBarColor = resources.getColor(R.color.main2Blue)
                        navigation.setBackgroundColor(resources.getColor(R.color.main2DeepBlue))
                        resources.getColor(R.color.main2Blue)
                    }
                }
            )
        )
    }

    override fun changeUI(mList: ArrayList<Fragment?>) {
        themeChange()
        Glide.with(this).load(R.drawable.defaults).into(logo_splash2)
        adapter = PagerAdapter(supportFragmentManager, mList)
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Notification)))
        navigation.addTab(navigation.newTab().setText("내 정보"))
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.searcher)))
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Setting)))
        pager.adapter = adapter
        pager.offscreenPageLimit = mList.size
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(navigation))
        navigation.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (menuItem != null)
                    when (tab?.position) {
                        Util.STATE_SEARCHER -> {
                            menuItem?.isVisible = true
                        }
                        else -> {
                            menuItem?.isVisible = false
                        }
                    }
                Util.state = tab?.position ?: Util.STATE_NOTIFICATION
                pager.currentItem = tab?.position ?: Util.STATE_NOTIFICATION

            }
        })

    }

    @SuppressLint("ApplySharedPref")
    override fun updatedContents() {
        val year = Util.YEAR
        val semester = Util.SEMESTER.toString()
        Util.sharedPreferences.edit().apply {
            putBoolean(Util.version, true)

            // 다음 업데이트시 삭제
            putString("2019-3-1", "<nodata>")
            putString("2019-3-2", "<nodata>")
            putString("2019-3-3", "<nodata>")
            putString("2019-3-4", "<nodata>")
            //
            for (x in arrayOf("global", "medical")) {
                putString("$year-$semester-1-$x", "<nodata>")
                putString("$year-$semester-2-$x", "<nodata>")
                putString("$year-$semester-3-$x", "<nodata>")
                putString("$year-$semester-4-$x", "<nodata>")
            }
        }.commit()
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("${Util.version} 버전 업데이트")
        builder.setMessage(" ${resources.getString(R.string.update)}")
        builder.setPositiveButton(
            "OK"
        ) { _, _ -> }
        builder.show()
    }


    override fun onBackPressed() {
        when (Util.state) {
            Util.STATE_NOTIFICATION -> {
                if (System.currentTimeMillis() > backKeyPressedTime + 2000L) {
                    backKeyPressedTime = System.currentTimeMillis()
                    Toast.makeText(applicationContext, "종료하시려면 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                }
            }
            Util.STATE_INFORMATION -> {
                pager.currentItem = Util.STATE_NOTIFICATION
                Util.state = Util.STATE_NOTIFICATION
            }
            Util.STATE_SEARCHER -> {
//                if (!mPresenter.floatingButtonControl()) {
                pager.currentItem = Util.STATE_NOTIFICATION
                Util.state = Util.STATE_NOTIFICATION
//                }
            }
            Util.STATE_SETTING -> {
                pager.currentItem = Util.STATE_NOTIFICATION
                Util.state = Util.STATE_NOTIFICATION
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

    private fun invisible() {
        if (!Util.novisible) {
            main_main.visibility = View.GONE
            main_splash.visibility = View.VISIBLE
            main_splash.invalidate()
            main_main.invalidate()
        }
    }

    override fun onAttachedToWindow() {
        visible()
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        invisible()
        super.onDetachedFromWindow()
    }

    override fun changeTheme() {
        mPresenter.changeThemes()
    }
}