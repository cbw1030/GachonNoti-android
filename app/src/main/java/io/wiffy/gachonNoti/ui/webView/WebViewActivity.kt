package io.wiffy.gachonNoti.ui.webView

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.Parse
import kotlinx.android.synthetic.main.activity_webview.*
import java.lang.Exception

class WebViewActivity : AppCompatActivity(), WebViewContract.View {

    lateinit var mPresenter: WebViewPresenter
    lateinit var builder: Dialog
    lateinit var bundle: Parse

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        initBuild()
        bundle = (intent.getSerializableExtra("bundle") as Parse)
        title = "${bundle.value} ${bundle.data}"
        webview_text.text = bundle.text
        window.statusBarColor = resources.getColor(R.color.mainBlue)
        mPresenter = WebViewPresenter(this)
        mPresenter.initPresent(bundle.link)
        themeChange()
    }

    private fun initBuild() {
        builder = Dialog(this@WebViewActivity)
        builder.setContentView(R.layout.builder)
        builder.setCancelable(false)
        builder.setCanceledOnTouchOutside(false)
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    override fun builderUp() {
        builder.show()
    }

    override fun builderDismiss() {
        builder.dismiss()
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun changeUI(javaS: String) {
        try{
            webview.loadDataWithBaseURL("", javaS, "text/html", "UTF-8", "");
            webview.settings?.javaScriptEnabled = true
        }catch (e:Exception){

        }
    }

    override fun onUserLeaveHint() {
        invisible()
        super.onUserLeaveHint()
    }

    private fun visible() {
        webview_layout.visibility = View.VISIBLE
        web_splash.visibility = View.GONE
        webview_layout.invalidate()
        web_splash.invalidate()
    }

    private fun invisible() {
        if (!Util.novisible) {
            webview_layout.visibility = View.GONE
            web_splash.visibility = View.VISIBLE
            webview_layout.invalidate()
            web_splash.invalidate()
        }
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
        Util.novisible = false
        visible()
        super.onResume()
    }


    override fun onPause() {
        invisible()
        super.onPause()
    }

    override fun onStop() {
        invisible()
        Util.novisible = false
        super.onStop()
    }

    override fun onDetachedFromWindow() {
        invisible()
        super.onDetachedFromWindow()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Util.novisible = true
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mymenu, menu)
        return true
    }

    private fun goPage() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(bundle.link)))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.actionmy -> {
            goPage()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }


    private fun themeChange() {
        supportActionBar!!.setBackgroundDrawable(
            ColorDrawable(
                when (Util.theme) {
                    "red" -> {
                        webview_layout.setBackgroundColor(resources.getColor(R.color.deepRed))
                        window.statusBarColor = resources.getColor(R.color.red)
                        resources.getColor(R.color.red)
                    }
                    "green" -> {
                        webview_layout.setBackgroundColor(resources.getColor(R.color.deepGreen))
                        window.statusBarColor = resources.getColor(R.color.green)
                        resources.getColor(R.color.green)
                    }
                    else -> {
                        webview_layout.setBackgroundColor(resources.getColor(R.color.main2DeepBlue))
                        window.statusBarColor = resources.getColor(R.color.main2Blue)
                        resources.getColor(R.color.main2Blue)
                    }
                }
            )
        )
    }
}