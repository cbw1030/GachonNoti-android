package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import java.util.*


class MyApplication : Application() {


    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()

        Util.sharedPreferences = getSharedPreferences(Util.appConstantPreferences, Context.MODE_PRIVATE)
        Util.firstBoot = Util.sharedPreferences.getBoolean(
            "firstBooting", true
        )
        Util.notifiSet = Util.sharedPreferences.getBoolean(
            "notiOn", true
        )
        Util.theme = Util.sharedPreferences.getString(
            "theme", "default"
        ) ?: "default"

        Util.seek = Util.sharedPreferences.getInt(
            "seek", 20
        )
        Util.initCount = booleanArrayOf(false,false,false,false)

        Util.campus = Util.sharedPreferences.getBoolean("campus", true)

        Util.YEAR = Calendar.getInstance().get(Calendar.YEAR).toString()
        Util.SEMESTER = when (Calendar.getInstance().get(Calendar.MONTH)) {
            in 2..5 -> 1
            in 6..7 -> 3
            in 8..11 -> 2
            else -> 4
        }
    }
}