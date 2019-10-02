package io.wiffy.gachonNoti.function

import android.app.Activity
import android.content.Context
import android.graphics.Point
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import io.wiffy.extension.restartApp
import io.wiffy.gachonNoti.`object`.Component

fun getScreenSize(activity: Activity) =
    Point().apply {
        activity.windowManager.defaultDisplay.getSize(this)
    }.run {
        Component.deviceHeight = y
        Component.deviceWidth = x
    }

fun doneLogin(act: Activity, cnt: Context) {
    if (Component.error)
        MaterialAlertDialogBuilder(act).apply {
            setTitle("세션이 만료되었습니다.")
            setMessage("앱을 재실행 합니다.")
            setPositiveButton(
                "OK"
            ) { _, _ -> restartApp(cnt) }
            setCancelable(false)
        }.show()
    Component.error = false
}