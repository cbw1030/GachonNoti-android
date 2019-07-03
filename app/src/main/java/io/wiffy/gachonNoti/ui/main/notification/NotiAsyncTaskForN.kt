package io.wiffy.gachonNoti.ui.main.notification

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import org.jsoup.Jsoup
import java.lang.StringBuilder
import java.net.URL


class NotiAsyncTaskForN(private val list: ParseList, private val mPresenter: NotificationPresenter) :
    AsyncTask<Void, Void, Int>() {

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            //mPresenter.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected()) return -1
        val address = "${Util.mobileURL1}${Util.index}${Util.mobileURL2}${Util.seek}${Util.mobileURL3}"
        val conn = Jsoup.parseBodyFragment(URL(address).readText()).select("div.list li")
        for (n in conn) {
            if (n.html().contains("alt=\"공지\"")) {
                val x = n.select("a").text()
                val v = x.split("]")[0]
                list.add(
                    Parse(
                        "$v]",
                        x.substring(v.length + 2),
                        n.select("span.data").text(),
                        true,
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
                        },
                        "http://m.gachon.ac.kr/gachon/${n.select("a").attr("href")}"
                    )
                )
            }
        }
        val help = URL("http://wiffy.io/gachon/thanks.txt").readText().split(",")
        val newString = StringBuilder()
        for (k in help) {
            newString.append("$k\n")
        }
        Util.helper = newString.toString()
        return 0
    }


    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (result == 0) {
                mPresenter.internetNotInterrupted()
                Util.index = 0
                mPresenter.request()
            } else {
                mPresenter.internetInterrupted()
            }
        }
    }
}