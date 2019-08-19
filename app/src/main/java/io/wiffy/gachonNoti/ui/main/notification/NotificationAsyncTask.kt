package io.wiffy.gachonNoti.ui.main.notification

import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.func.ACTION_FAILURE
import io.wiffy.gachonNoti.func.ACTION_SUCCESS
import io.wiffy.gachonNoti.func.isNetworkConnected
import io.wiffy.gachonNoti.model.Parse
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Component
import io.wiffy.gachonNoti.model.SuperContract
import org.jsoup.Jsoup
import java.net.URL


class NotificationAsyncTask(
    private val list: ParseList,
    private val mPresenter: NotificationContract.Presenter,
    private val type: Int,
    private val keyword: String,
    private val pageNum: Int
) : SuperContract.SuperAsyncTask() {

//        TYPE
//        0 -> NOTIFICATION
//        1 -> NEWS
//        2 -> EVENT
//        3 -> SCHOLARSHIP

    private val address = setAddress(type)

    private fun setAddress(type: Int) = "http://m.gachon.ac.kr/gachon/notice.jsp?${when (type) {
        0 -> "&pageSize=&boardType_seq=358"
        1 -> "&pageSize=&boardType_seq=359"
        2 -> "&pageSize=&boardType_seq=360"
        3 -> "&pageSize=&boardType_seq=361"
        else -> "&pageSize=&boardType_seq=358"
    }}&pageNum=$pageNum&searchword=$keyword&searchopt=title"

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!isNetworkConnected(mPresenter.getContext()!!)) return ACTION_FAILURE
        return try {
            if (keyword == "" && list.isEmpty()) {
                request(true)
            }
            request(false)

            try {
                Component.helper = URL("http://wiffy.io/gachon/thanks.txt").readText()
            } catch (e: Exception) {
            }

            ACTION_SUCCESS
        } catch (e: Exception) {
            33
        }
    }

    private fun request(bool: Boolean) {
        val conn = Jsoup.parseBodyFragment(URL(address).readText()).select("div.list li")
        for (n in conn) {
            if (n.html().contains("등록된 정보가 없습니다.")) {
                continue
            }
            if (bool && n.html().contains("alt=\"공지\"")) {
                val x = n.select("a").text()
                val v = x.split("]")[0]
                list.add(
                    Parse(
                        "$v]",
                        x.substring(v.length + 2),
                        n.select("span.data").text(),
                        true,
                        n.html().contains("icon_new.gif"),
                        n.html().contains("icon_file.gif"),
                        "http://m.gachon.ac.kr/gachon/${n.select("a").attr("href")}"
                    )
                )
            } else if (!n.html().contains("alt=\"공지\"")) {
                val x = n.select("a").text()
                if (type == 1) {
                    list.add(
                        Parse(
                            "",
                            x,
                            n.select("span.data").text(),
                            false,
                            n.html().contains("icon_new.gif"),
                            n.html().contains("icon_file.gif"),
                            "http://m.gachon.ac.kr/gachon/${n.select("a").attr("href")}"
                        )
                    )
                } else {
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
        }
    }

    override fun onPostExecute(result: Int?) {
        with(mPresenter) {
            Handler(Looper.getMainLooper()).post {
                when (result) {
                    ACTION_SUCCESS -> {
                        mPresenter.dismiss()
                        update(list)
                    }
                    33 -> {
                        dismiss()
                        list.clear()
                        update(list)
                    }
                    else -> {
                        dismiss()
                        internetInterrupted()
                    }
                }
            }
        }
    }
}