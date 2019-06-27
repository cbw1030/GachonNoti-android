package io.wiffy.gachonNoti.ui.webView

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.notification.Parse
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    lateinit var parse: Parse
    lateinit var string: String

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        parse = intent.getSerializableExtra("bundle") as Parse
        webview.settings.javaScriptEnabled = true
        webview.loadUrl(parse.link)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onUserLeaveHint() {
        invisible()
        super.onUserLeaveHint()
    }

    private fun visible() {
        webview.visibility = View.VISIBLE
        web_splash.visibility = View.GONE
        webview.invalidate()
        web_splash.invalidate()
    }

    private fun invisible() {
        webview.visibility = View.GONE
        web_splash.visibility = View.VISIBLE
        webview.invalidate()
        web_splash.invalidate()
    }

    override fun onAttachedToWindow() {
        visible()
        super.onAttachedToWindow()
    }

    override fun onStart() {
        visible()
        super.onStart()
    }

    override fun onResume() {
        visible()
        super.onResume()
    }


    override fun onPause() {
        invisible()
        super.onPause()
    }

    override fun onStop() {
        invisible()
        super.onStop()
    }

    override fun onDetachedFromWindow() {
        invisible()
        super.onDetachedFromWindow()
    }
}