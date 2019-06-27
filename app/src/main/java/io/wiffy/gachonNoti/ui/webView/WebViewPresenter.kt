package io.wiffy.gachonNoti.ui.webView

class WebViewPresenter(private val mView:WebViewContract.View):WebViewContract.Presenter {
    override fun initPresent() {
        mView.changeUI()
    }
}