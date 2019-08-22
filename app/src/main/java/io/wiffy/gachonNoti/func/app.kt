package io.wiffy.gachonNoti.func

import android.app.Activity
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
