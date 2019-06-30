package io.wiffy.gachonNoti.model


import android.annotation.SuppressLint
import android.content.SharedPreferences
import io.wiffy.gachonNoti.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class Util {
    companion object {
        @JvmStatic
        lateinit var sharedPreferences: SharedPreferences

        @JvmStatic
        var seek = 20

        @JvmStatic
        var firstBoot = true

        @JvmStatic
        var index = 0

        @JvmStatic
        var looper = true

        @JvmStatic
        var notifiSet = true

        @JvmStatic
        var state = 0

        @JvmStatic
        var helper = "인터넷 연결을 확인해주세요."

        @JvmStatic
        var theme = "default"

        @JvmStatic
        var made = true

        @JvmStatic
        var novisible = false

        @JvmStatic
        fun getRandomColorId(): Int = intArrayOf(
            R.color.ran1,
            R.color.ran2,
            R.color.ran3,
            R.color.ran4,
            R.color.ran5,
            R.color.ran6,
            R.color.ran7,
            R.color.ran8,
            R.color.ran9,
            R.color.ran10,
            R.color.ran11,
            R.color.ran12,
            R.color.ran13
        )[Random().nextInt(14)]


        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun classToTime(time: String): LongArray {
            val dt = SimpleDateFormat("HH:mm:ss")
            var start: String
            var end: String
            try {
                when (time) {
                    "A" -> {
                        start = "09:30:00"
                        end = "10:45:00"
                    }
                    "B" -> {
                        start = "11:00:00"
                        end = "12:15:00"
                    }
                    "C" -> {
                        start = "12:30:00"
                        end = "13:45:00"
                    }
                    "D" -> {
                        start = "14:00:00"
                        end = "15:15:00"
                    }
                    "E" -> {
                        start = "15:30:00"
                        end = "16:45:00"
                    }
                    else -> {

                        when (time.toInt()) {
                            in 1..8 -> {
                                start = "${(time.toInt() + 8)}:00:00"
                                end = "${(time.toInt() + 8)}:50:00"
                            }
                            9 -> {
                                start = "17:30:00"
                                end = "18:20:00"
                            }
                            10 -> {
                                start = "18:25:00"
                                end = "19:15:00"
                            }
                            11 -> {
                                start = "19:20:00"
                                end = "20:10:00"
                            }
                            12 -> {
                                start = "20:15:00"
                                end = "21:05:00"
                            }
                            13 -> {
                                start = "21:10:00"
                                end = "22:00:00"
                            }
                            14 -> {
                                start = "22:05:00"
                                end = "22:55:00"
                            }
                            else -> {
                                start = "00:00:00"
                                end = "00:00:00"
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                start = "00:00:00"
                end = "00:00:00"
            }



            return longArrayOf(dt.parse(start).time, dt.parse(end).time)
        }


        const val mobileURL1 = "http://m.gachon.ac.kr/gachon/notice.jsp?pageNum="
        const val mobileURL2 = "&pageSize="
        const val mobileURL3 = "&boardType_seq=358&approve=&secret=&answer=&branch=&searchopt=&searchword="
        const val appConstantPreferences = "GACHONNOTICE"
        const val RESULT_SETTING_CHANGED = 123

        const val STATE_NOTIFICATION = 0
        const val STATE_SEARCHER = 1
        const val STATE_SETTING = 2
        const val STATE_WEBVIEW = 3

        const val MONDAY = 0
        const val TUESDAY = 1
        const val WEDNESDAY = 2
        const val THURSDAY = 3
        const val FRIDAY = 4

    }
}