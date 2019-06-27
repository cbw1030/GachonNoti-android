package io.wiffy.gachonNoti.ui.webView

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.notification.Parse
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {
    lateinit var parse: Parse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        parse = intent.getSerializableExtra("bundle") as Parse
        webview.loadUrl(parse.link)

    }
}