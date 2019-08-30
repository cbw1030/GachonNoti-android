package io.wiffy.gachonNoti.ui.webView

class WebViewPresenter(private val mView: WebViewContract.View, private val url: String) : WebViewContract.Presenter {

    override fun initPresent() {
        WebAsyncTask(url, this, false).execute()
    }

    override fun updateWeb(javaS: String) = mView.changeUI(javaS)


    override fun builderUp() = mView.builderUp()


    override fun builderDismiss() = mView.builderDismiss()
}