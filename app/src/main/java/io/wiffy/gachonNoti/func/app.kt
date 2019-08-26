package io.wiffy.gachonNoti.func

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Point
import io.wiffy.gachonNoti.`object`.Component
import kotlin.math.roundToInt
import kotlin.system.exitProcess

fun restartApp(context: Context) {
    context.startActivity(
        Intent.makeRestartActivityTask(
            context.packageManager.getLaunchIntentForPackage(context.packageName)?.component
        )
    )
    exitProcess(0)
}

fun getScreenSize(activity: Activity) =
    Point().apply {
        activity.windowManager.defaultDisplay.getSize(this)
    }.run {
        Component.deviceHeight = y
        Component.deviceWidth = x
    }

fun dpToPx(context: Context, dp: Int) = (dp * context.resources.displayMetrics.density).roundToInt()

fun doneLogin(act: Activity, cnt: Context) {
    if (Component.error)
        AlertDialog.Builder(act).apply {
            setTitle("세션이 만료되었습니다.")
            setMessage("앱을 재실행 합니다.")
            setPositiveButton(
                "OK"
            ) { _, _ -> restartApp(cnt) }
            setCancelable(false)
        }.show()
    Component.error = false
}