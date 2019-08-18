package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import io.wiffy.gachonNoti.func.appConstantPreferences
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.func.sharedPreferences
import java.util.*


class MyApplication : Application() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(appConstantPreferences, Context.MODE_PRIVATE)

        Util.firstBoot = getSharedItem("firstBooting", true)
        Util.notificationSet = getSharedItem("notiOn", true)
        Util.theme = getSharedItem("theme", "default")
        Util.isLogin = getSharedItem("login", false)
        Util.campus = getSharedItem("campus", true)

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