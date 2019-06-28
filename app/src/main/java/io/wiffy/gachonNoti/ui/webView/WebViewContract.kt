package io.wiffy.gachonNoti.ui.webView

interface WebViewContract
{
    interface View
    {
        fun changeUI(javaS:String)
        fun builderDismiss()
        fun builderUp()
    }

    interface Presenter
    {
        fun initPresent(url :String)
        fun updateWeb(javaS:String)
        fun builderDismiss()
        fun builderUp()
    }

}