package io.wiffy.gachonNoti.ui.webView

interface WebViewContract
{
    interface View
    {
        fun changeUI()
    }

    interface Presenter
    {
        fun initPresent()
    }

}