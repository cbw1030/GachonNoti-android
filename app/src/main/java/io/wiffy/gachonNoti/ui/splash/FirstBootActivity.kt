package io.wiffy.gachonNoti.ui.splash

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.first_boot.*
import java.util.*
import kotlin.collections.ArrayList

class FirstBootActivity : AppCompatActivity() {
    private val list = ArrayList<RelativeLayout>()
    private val list2 = ArrayList<Locale>()
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_boot)
        list.add(lang_EN2)
        list.add(lang_KO2)
        list.add(lang_ZH2)
        list.add(lang_TW2)
        list.add(lang_JA2)
        list2.add(Locale.ENGLISH)
        list2.add(Locale.KOREAN)
        list2.add(Locale.CHINESE)
        list2.add(Locale.TAIWAN)
        list2.add(Locale.JAPANESE)

        for (X in 0 until list.size) {
            list[X].setOnClickListener {
                Util.sharedPreferences.edit().putString("global", list2[X].toLanguageTag()).apply()
                Util.global = list2[X].toLanguageTag()
                setResult(Activity.RESULT_OK)
                finish()
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            Util.wrap(
                newBase,
                Util.global
            )
        )
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }
}