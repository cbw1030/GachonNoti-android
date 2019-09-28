package io.wiffy.gachonNoti.function

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@SuppressLint("SimpleDateFormat")
fun calculateDateDifference(date: String): Int = try {
    val format = SimpleDateFormat("yyyy-mm-dd")
    abs((format.parse(format.format(Date(System.currentTimeMillis()))).time - format.parse(date).time) / (24 * 60 * 60 * 1000)).toInt()
} catch (e: Exception) {
    7
}

@SuppressLint("SimpleDateFormat")
fun calculateDateIntegerDifference(date: String): Int = try {
    val format = SimpleDateFormat("yyyy-mm-dd")
    ((format.parse(date).time - format.parse(format.format(Date(System.currentTimeMillis()))).time) / (24 * 60 * 60 * 1000)).toInt()
} catch (e: Exception) {
    7
}