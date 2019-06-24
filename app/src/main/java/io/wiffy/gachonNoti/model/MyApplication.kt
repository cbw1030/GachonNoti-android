package io.wiffy.gachonNoti.model

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

import java.util.*
import kotlin.collections.HashMap

class MyApplication:Application() {


    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()

        Util.sharedPreferences = getSharedPreferences(Util.appConstantPreferences, Context.MODE_PRIVATE)
        Util.firstBoot=Util.sharedPreferences.getBoolean(
            "firstBooting",true
        )


    }
}