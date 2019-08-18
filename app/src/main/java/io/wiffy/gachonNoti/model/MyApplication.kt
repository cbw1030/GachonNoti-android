package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import io.wiffy.gachonNoti.func.appConstantPreferences
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.func.sharedPreferences
import java.util.*


class MyApplication : Application(), SuperContract.WiffyObject {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sharedPreferences = getSharedPreferences(appConstantPreferences, Context.MODE_PRIVATE)

        Component.firstBoot = getSharedItem("firstBooting", true)
        Component.notificationSet = getSharedItem("notiOn", true)
        Component.theme = getSharedItem("theme", "default")
        Component.isLogin = getSharedItem("login", false)
        Component.campus = getSharedItem("campus", true)

        Component.version =
            applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0).versionName
        Component.YEAR = Calendar.getInstance().get(Calendar.YEAR).toString()
        Component.SEMESTER = when (Calendar.getInstance().get(Calendar.MONTH)) {
            in 2..5 -> 1
            in 6..7 -> 3
            in 8..11 -> 2
            else -> 4
        }
    }
}