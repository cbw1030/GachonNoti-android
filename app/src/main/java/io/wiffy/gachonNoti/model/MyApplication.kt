package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import java.util.*

class MyApplication:Application() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        Util.sharedPreferences = getSharedPreferences(Util.appConstantPreferences, Context.MODE_PRIVATE)
        Util.global = Util.sharedPreferences.getString(
            "global",Locale.ENGLISH.toLanguageTag()
        )?:Locale.ENGLISH.toLanguageTag()

        Util.firstBoot=Util.sharedPreferences.getBoolean(
            "firstBoot",true
        )

    }
}