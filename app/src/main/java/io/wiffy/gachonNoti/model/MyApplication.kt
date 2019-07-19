package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.util.*


class MyApplication : Application() {


    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        Util.sharedPreferences = getSharedPreferences(Util.appConstantPreferences, Context.MODE_PRIVATE).apply {
            Util.firstBoot = getBoolean("firstBooting", true)
            Util.notificationSet = getBoolean("notiOn", true)
            Util.theme = getString("theme", "default") ?: "default"
            Util.seek = getInt("seek", 20)
            Util.initCount = booleanArrayOf(false, false, false, false)
            Util.isLogin = getBoolean("login", false)
            Util.campus = getBoolean("campus", true)
        }
        Util.version = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName
        Util.YEAR = Calendar.getInstance().get(Calendar.YEAR).toString()
        Util.SEMESTER = when (Calendar.getInstance().get(Calendar.MONTH)) {
            in 2..5 -> 1
            in 6..7 -> 3
            in 8..11 -> 2
            else -> 4
        }
    }
}