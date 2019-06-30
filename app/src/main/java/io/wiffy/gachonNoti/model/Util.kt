package io.wiffy.gachonNoti.model


import android.annotation.SuppressLint
import android.content.SharedPreferences
import java.lang.Exception
import java.text.SimpleDateFormat

class Util {
    companion object {
        @JvmStatic
        lateinit var sharedPreferences: SharedPreferences

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
        fun timeToClass(hour: Int, minute: Int, noon: Boolean): ArrayList<String> {
            // 오전 true 오후 false , hour minute
            val myList = ArrayList<String>()

            val absoluteMin = minute + 60 * if (noon) {
                hour - 9
            } else {
                hour - 3
            }

            val relativeHour = absoluteMin / 60 + 1
            val relativeMin = absoluteMin % 60

            if (relativeHour <= 14 &&
                relativeMin < 50
            ) myList.add(relativeHour.toString())

            when (absoluteMin) {
                in 30 until 105 -> myList.add("A")
                in 120 until 195 -> myList.add("B")
                in 210 until 285 -> myList.add("C")
                in 300 until 375 -> myList.add("D")
                in 390 until 465 -> myList.add("E")
                else -> {
                }
            }

            return myList
        }

        @SuppressLint("SimpleDateFormat")
        @JvmStatic
        fun classToTime(time: String): LongArray {
            val dt = SimpleDateFormat("HH:mm:ss")
            var start: String
            var end: String

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
                    try {
                        if (time.toInt() <= 14) {
                            start = "${(time.toInt() + 9)}:00:00"
                            end = "${(time.toInt() + 9)}:50:00"
                        }else{
                            start = "00:00:00"
                            end = "00:00:00"
                        }
                    } catch (e: Exception) {
                        start = "00:00:00"
                        end = "00:00:00"
                    }
                }
            }

            return longArrayOf(dt.parse(start).time,dt.parse(end).time)
        }


        const val mobileURL1 = "http://m.gachon.ac.kr/gachon/notice.jsp?pageNum="
        const val mobileURL2 = "&pageSize=30&boardType_seq=358&approve=&secret=&answer=&branch=&searchopt=&searchword="
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