package io.wiffy.gachonNoti.ui.webView

import io.wiffy.gachonNoti.model.SuperContract

interface WebViewContract {

    abstract class View : SuperContract.SuperActivity() {
        abstract fun changeUI(javaS: String)
        abstract fun builderDismiss()
        abstract fun builderUp()
        abstract fun mainNotification()
    }

    interface Presenter : SuperContract.SuperPresenter {
        fun updateWeb(javaS: String)
        fun builderDismiss()
        fun builderUp()
    }

}