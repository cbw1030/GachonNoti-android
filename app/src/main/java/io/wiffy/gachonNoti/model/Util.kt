package io.wiffy.gachonNoti.model

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.LocaleList
import java.util.*
import kotlin.system.exitProcess

class Util {
    companion object {
        @JvmStatic
        lateinit var sharedPreferences: SharedPreferences
        //        @JvmStatic
//        lateinit var global: String
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
        fun wrap(context: Context?, language: String?): ContextWrapper? {
            val configuration = context?.resources?.configuration
            val newLocale = Locale(language)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration?.setLocale(newLocale)
                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)

            } else {
                configuration?.setLocale(newLocale)
            }
            return ContextWrapper(context?.createConfigurationContext(configuration!!))
        }

        @JvmStatic
        fun restartApp(context: Context) {
            context.startActivity(
                Intent.makeRestartActivityTask(
                    context.packageManager.getLaunchIntentForPackage(context.packageName)?.component
                )
            )
            exitProcess(0)
        }

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