package io.wiffy.gachonNoti.model


import android.content.SharedPreferences

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


        const val mobileURL1 = "http://m.gachon.ac.kr/gachon/notice.jsp?pageNum="
        const val mobileURL2 = "&pageSize=30&boardType_seq=358&approve=&secret=&answer=&branch=&searchopt=&searchword="
        const val appConstantPreferences = "GACHONNOTICE"
        const val RESULT_SETTING_CHANGED = 123

        const val STATE_NOTIFICATION = 0
        const val STATE_SEARCHER=1
        const val STATE_SETTING = 2
        const val STATE_WEBVIEW = 3
    }
}