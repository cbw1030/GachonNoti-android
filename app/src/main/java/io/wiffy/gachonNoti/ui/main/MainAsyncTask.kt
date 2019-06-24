package io.wiffy.gachonNoti.ui.main

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.Parse
import io.wiffy.gachonNoti.ui.main.notification.ParseList
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class MainAsyncTask(private val list: ParseList, private val mView: MainContract.View) : AsyncTask<Void, Void, Int>() {


    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mView.builderUp()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        val address = "${Util.mobileURL1}${Util.index}${Util.mobileURL2}"
        val conn = Jsoup.parseBodyFragment(URL(address).readText()).select("div.list li")
        for (n in conn) {
            if (!n.html().contains("alt=\"공지\"")) {
                val x = n.select("a").text()
                val v = x.split("]")[0]

                list.add(
                    Parse(
                        "$v]",
                        x.substring(v.length + 2),
                        n.select("span.data").text()
                    )
                )
            }
        }
        return 0
    }


    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {

            mView.builderDismiss()

            if(Util.looper)
            if (Util.index == 0) {
                mView.init()
            } else {

            }
            Util.index += 1
        }
    }
}