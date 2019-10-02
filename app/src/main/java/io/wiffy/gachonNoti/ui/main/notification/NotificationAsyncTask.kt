package io.wiffy.gachonNoti.ui.main.notification

import android.os.Handler
import android.os.Looper
import io.wiffy.extension.isNetworkConnected
import io.wiffy.gachonNoti.utils.ACTION_FAILURE
import io.wiffy.gachonNoti.utils.ACTION_SUCCESS
import io.wiffy.gachonNoti.model.Parse
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.`object`.Component
import io.wiffy.gachonNoti.utils.ACTION_SUCCESS2
import io.wiffy.gachonNoti.model.SuperContract
import org.jsoup.Jsoup
import java.net.URL


class NotificationAsyncTask(
    private val list: ParseList,
    private val mPresenter: NotificationContract.Presenter,
    private val type: Int,
    private val keyword: String,
    private val pageNum: Int
) : SuperContract.SuperAsyncTask<Void, Void, Int>() {

//        TYPE
//        0 -> NOTIFICATION
//        1 -> NEWS
//        2 -> EVENT
//        3 -> SCHOLARSHIP

    private val address = setAddress(type)

    private fun setAddress(type: Int) =
        "http://m.gachon.ac.kr/gachon/notice.jsp?&pageSize=&boardType_seq=${when (type) {
            0 -> "358"
            1 -> "359"
            2 -> "360"
            3 -> "361"
            else -> "358"
        }}&pageNum=$pageNum&searchword=$keyword&searchopt=title"

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            Component.getBuilder()?.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!isNetworkConnected(mPresenter.getContext()!!)) {
            return ACTION_FAILURE
        }
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
            ACTION_SUCCESS2
        }
    }

    private fun request(bool: Boolean) {
        for (n in Jsoup.parseBodyFragment(URL(address).readText()).select("div.list li")) {
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
            } else if (!bool && !n.html().contains("alt=\"공지\"")) {
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
                Component.getBuilder()?.dismiss()
                when (result) {
                    ACTION_SUCCESS -> {
                        update(list)
                    }
                    ACTION_SUCCESS2 -> {
                        update(list)
                    }
                    else -> {
                        internetInterrupted()
                    }
                }
            }
        }
    }
}