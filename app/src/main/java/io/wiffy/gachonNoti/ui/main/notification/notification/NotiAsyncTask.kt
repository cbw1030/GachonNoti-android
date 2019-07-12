package io.wiffy.gachonNoti.ui.main.notification.notification

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.Parse
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util
import org.jsoup.Jsoup
import java.net.URL


class NotiAsyncTask(
    private val list: ParseList,
    private val mPresenter: NotificationPresenter,
    private val context: Context?
) :
    AsyncTask<Void, Void, Int>() {

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context!!)) return Util.ACTION_FAILURE

        try {
            val address =
                "http://m.gachon.ac.kr/gachon/notice.jsp?pageNum=${Util.NotificationIndex}&pageSize=${Util.seek}&boardType_seq=358&approve=&secret=&answer=&branch=&searchopt=&searchword="
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
                                n.html().contains("icon_new.gif"),
                                n.html().contains("icon_file.gif"),
                            "http://m.gachon.ac.kr/gachon/${n.select("a").attr("href")}"
                        )
                    )
                }
            }
            return Util.ACTION_SUCCESS
        } catch (e: Exception) {
            return Util.ACTION_FAILURE
        }

    }

    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (result == Util.ACTION_SUCCESS) {
                if(!Util.initCount.contains(false))
                mPresenter.dismiss()
                mPresenter.update(list)
                Util.NotificationIndex += 1
            } else {
                mPresenter.dismiss()
                mPresenter.internetInterrupted()
            }
        }
    }
}