package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context


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
            "seek", 10
        )
    }
}