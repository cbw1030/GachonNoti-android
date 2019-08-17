package io.wiffy.gachonNoti.ui.webView

import androidx.appcompat.app.AppCompatActivity

interface WebViewContract {

    abstract class View : AppCompatActivity() {
        abstract fun changeUI(javaS: String)
        abstract fun builderDismiss()
        abstract fun builderUp()
    }

    interface Presenter {
        fun initPresent(url: String)
        fun updateWeb(javaS: String)
        fun builderDismiss()
        fun builderUp()
    }

}