package io.wiffy.gachonNoti.ui.main.notification

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import io.wiffy.gachonNoti.ui.main.notification.Parse
import io.wiffy.gachonNoti.ui.main.notification.ParseList
import org.json.JSONObject
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class NotiAsyncTask(private val list: ParseList, private val mPresenter: MainContract.PresenterNotification) :
    AsyncTask<Void, Void, Int>() {

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.show()
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
                        n.select("span.data").text(),
                        false,
                        when {
                            n.html().contains("icon_new.gif") -> {
                                true
                            }
                            else -> {
                                false
                            }
                        },
                        when {
                            n.html().contains("icon_file.gif") -> {
                                true
                            }
                            else -> {
                                false
                            }
                        }
                    )
                )
            }
        }
        val help = URL("http://wiffy.io/gachon/thanks.txt").readText().split(",")
        val newString= StringBuilder()
        for (k in help) {
            newString.append("$k\n")
        }
        Util.helper = newString.toString()
        return 0
    }


    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            mPresenter.dismiss()
            mPresenter.update(list)
            Util.index += 1
        }
    }
}