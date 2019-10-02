package io.wiffy.gachonNoti.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun calculateDateDifference(date: String): Int = try {
    (
            (Date().time - (SimpleDateFormat("yyyy-MM-dd").parse(
                date
            )?.time ?: 0L)) / (24 * 60 * 60 * 1000)
            ).toInt()
} catch (e: Exception) {
    7
}

@SuppressLint("SimpleDateFormat")
fun calculateDateIntegerDifference(date: String): Int = try {
    (((SimpleDateFormat("yyyy-MM-dd").parse(date)?.time ?: 0L)
            - Date().time) / (24 * 60 * 60 * 1000)).toInt()
} catch (e: Exception) {
    -1
}