package io.wiffy.gachonNoti.model

import android.app.Application
import android.content.Context
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.`object`.Component.sharedPreferences
import java.util.*


class MyApplication : Application(), SuperContract.WiffyObject {

    override fun onCreate() {
        console("Application On")
        super.onCreate()
        sharedPreferences = getSharedPreferences(appConstantPreferences, Context.MODE_PRIVATE)

        Component.version =
            applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
                .versionName

        //None Reset Component on Update
        Component.firstBoot = getSharedItem("firstBooting", true)
        Component.notificationSet = getSharedItem("notiOn", true)
        Component.theme = getSharedItem("theme", "default")
        Component.campus = getSharedItem("campus", true)

        if (getSharedItem("preVersion", "") != Component.version) {
            resetSharedPreference()
            setSharedItem("preVersion", Component.version)
            setSharedItem("firstBooting", Component.firstBoot)
            setSharedItem("notiOn", Component.notificationSet)
            setSharedItem("theme", Component.theme)
            setSharedItem("campus", Component.campus)
        }

        //Reset Component on Update
        Component.isLogin = getSharedItem("login", false)
        Component.timeTableSet = getSharedItem("tableItems", HashSet())
        Component.adminMode = getSharedItem("ADMIN", false)
        Component.YEAR = if (getSharedItem("yearAuto", true)) {
            Calendar.getInstance().get(Calendar.YEAR).toString()

        } else {
            getSharedItem<Int>("year").toString()
        }

        // 학기 조정은 여기서 하면 편하다.
        Component.SEMESTER = if (getSharedItem("semesterAuto", true)) {
            when (Calendar.getInstance().get(Calendar.MONTH)) {
                in 2..5 -> 1
                in 6..7 -> 3
                in 8..11 -> 2
                else -> 4
            }

        } else {
            getSharedItem("semester")
        }

    }
}