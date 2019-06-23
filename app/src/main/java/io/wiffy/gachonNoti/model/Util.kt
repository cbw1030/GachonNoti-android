package io.wiffy.gachonNoti.model

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import android.os.Build
import android.os.LocaleList
import java.util.*

class Util {
    companion object{
        @JvmStatic
        lateinit var sharedPreferences:SharedPreferences
        @JvmStatic
        lateinit var global:String
        @JvmStatic
        var firstBoot = true

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

        const val papagoId = "_PhNYUBa_SGQ1Qm7NeSP"
        const val papagoSecret = "YgUYJ4N1co"
        const val appConstantPreferences = "GACHONNOTIFICATIONE"
    }
}