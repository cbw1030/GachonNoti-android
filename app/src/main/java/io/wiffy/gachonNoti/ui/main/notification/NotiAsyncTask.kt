package io.wiffy.gachonNoti.ui.main.notification

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.Util
import org.jsoup.Jsoup
import java.net.URL


class NotiAsyncTask(private val list: ParseList, private val mPresenter: NotificationPresenter,private val context: Context?) :
    AsyncTask<Void, Void, Int>() {

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context!!)) return -1

        try{
            val address = "${Util.mobileURL1}${Util.index}${Util.mobileURL2}${Util.seek}${Util.mobileURL3}"
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
                            },
                            "http://m.gachon.ac.kr/gachon/${n.select("a").attr("href")}"
                        )
                    )
                }
            }
            return 0
        }catch (e:Exception){
            return -1
        }

    }

    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (result == 0) {
                mPresenter.dismiss()
                mPresenter.update(list)
                Util.index += 1
            } else {
                mPresenter.dismiss()
                mPresenter.internetInterrupted()
            }
        }
    }
}