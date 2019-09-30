package io.wiffy.gachonNoti.function

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@SuppressLint("SimpleDateFormat")
fun calculateDateDifference(date: String): Int = try {
    abs(
        (Date().time - (SimpleDateFormat("yyyy-mm-dd").parse(
            date
        )?.time ?: 0L)) / (24 * 60 * 60 * 1000)
    ).toInt()
} catch (e: Exception) {
    7
}

@SuppressLint("SimpleDateFormat")
fun calculateDateIntegerDifference(date: String): Int = try {
    (((SimpleDateFormat("yyyy-mm-dd").parse(date)?.time ?: 0L)
            - Date().time) / (24 * 60 * 60 * 1000)).toInt()
} catch (e: Exception) {
    -1
}