package io.wiffy.gachonNoti.ui.main.setting


import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatRadioButton
import androidx.core.view.get
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*
import kotlin.collections.ArrayList

class LanguageActivity : AppCompatActivity() {
    var list = ArrayList<RelativeLayout>()
    var listRadio = ArrayList<AppCompatRadioButton>()
    var listString = ArrayList<String>()
    var listString2 = ArrayList<String>()
    var ln = -1
    var lastCheck = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_language)
        list.add(lang_EN)
        list.add(lang_KO)
        list.add(lang_ZH)
        list.add(lang_TW)
        list.add(lang_JA)
        listRadio.add(lang_EN[1] as AppCompatRadioButton)
        listRadio.add(lang_KO[1] as AppCompatRadioButton)
        listRadio.add(lang_ZH[1] as AppCompatRadioButton)
        listRadio.add(lang_TW[1] as AppCompatRadioButton)
        listRadio.add(lang_JA[1] as AppCompatRadioButton)
        listString.add("The system will be restarted!")
        listString.add("시스템이 재시작됩니다!")
        listString.add("系统将重新启动!")
        listString.add("系統將重新啟動!")
        listString.add("システムが再起動されます！")

        listString2.add("Language Setting")
        listString2.add("언어 설정")
        listString2.add("语言设置")
        listString2.add("語言設置")
        listString2.add("言語設定")
        ln =
            when (Util.global) {
                Locale.KOREAN.toLanguageTag() -> {
                    1
                }
                Locale.CHINESE.toLanguageTag() -> {
                    2
                }
                Locale.TAIWAN.toLanguageTag() -> {
                    3
                }
                Locale.JAPANESE.toLanguageTag() -> {
                    4
                }
                else -> {
                    0
                }
            }
        lastCheck = ln
        listRadio[ln].isChecked = true
        langSet.text = listString2[ln]

        for (RL in 0 until list.size) {
            list[RL].setOnClickListener {
                for (RB in 0 until listRadio.size) {
                    listRadio[RB].isChecked = when (RL) {
                        RB -> {
                            when (RL) {
                                ln -> {
                                    (makeText[0] as TextView).text = ""
                                }
                                else -> {
                                    (makeText[0] as TextView).text = listString[RL]
                                    langSet.text = listString2[ln]
                                }
                            }
                            true
                        }
                        else -> false
                    }
                }
                lastCheck = RL
            }
        }
        OK.setOnClickListener {
            if (lastCheck == ln) back()
            else {
                val languages =
                    when (lastCheck) {
                        1 -> {
                            Locale.KOREAN.toLanguageTag()
                        }
                        2 -> {
                            Locale.CHINESE.toLanguageTag()
                        }
                        3 -> {
                            Locale.TAIWAN.toLanguageTag()
                        }
                        4 -> {
                            Locale.JAPANESE.toLanguageTag()
                        }
                        else -> {
                            Locale.ENGLISH.toLanguageTag()
                        }
                    }
                Util.sharedPreferences.edit().putString("global", languages).commit()
                Util.restartApp(applicationContext)
            }
        }

        CANCEL.setOnClickListener {
            back()
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

    private fun back() {
        setResult(Util.languageResultOff)
        finish()
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }

    override fun onBackPressed() {
        back()
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }
}