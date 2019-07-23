package io.wiffy.gachonNoti.ui.main.notification

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.data.Parse
import io.wiffy.gachonNoti.model.data.ParseList
import io.wiffy.gachonNoti.model.Util
import org.jsoup.Jsoup
import java.net.URL


class NotificationComponentAsyncTask(
    private val list: ParseList,
    private val mPresenter: NotificationComponentContract.Presenter,
    private val context: Context?,
    private val type: Int,
    keyword: String?
) :
    AsyncTask<Void, Void, Int>() {

    //        TYPE
//        0 -> NOTIFICATION_FOR_N
//        1 -> NOTIFICATION
//        2 -> NEWS
//        3 -> EVENT
//        4 -> SCHOLARSHIP_FOR_N
//        5 -> SCHOLARSHIP

    private val address = "http://m.gachon.ac.kr/gachon/notice.jsp?pageNum=${when (type) {
        0, 1 -> "${Util.NotificationIndex}&pageSize=${if (keyword.isNullOrBlank()) {
            "20"
        } else {
            "100"
        }}&boardType_seq=358"
        2 -> "${Util.NewsIndex}&pageSize=${if (keyword.isNullOrBlank()) {
            "20"
        } else {
            "100"
        }}&boardType_seq=359"
        3 -> "${Util.EventIndex}&pageSize=${if (keyword.isNullOrBlank()) {
            "20"
        } else {
            "100"
        }}&boardType_seq=360"
        else -> "${Util.ScholarshipIndex}&pageSize=${if (keyword.isNullOrBlank()) {
            "20"
        } else {
            "100"
        }}&boardType_seq=361"
    }}&approve=&secret=&answer=&branch=&searchopt=title&searchword=${if (type == 0 || type == 4) {
        ""
    } else {
        keyword ?: ""
    }}"


    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            when (type) {
                0 -> {
                    if (!Util.initCount.contains(true))
                        mPresenter.show()
                    Util.initCount[0] = true
                }
                1 -> {
                    mPresenter.show()
                }
                2 -> {
                    if (!Util.initCount.contains(true))
                        mPresenter.show()
                    Util.initCount[1] = true
                }
                3 -> {
                    if (!Util.initCount.contains(true))
                        mPresenter.show()
                    Util.initCount[2] = true
                }
                4 -> {
                    if (!Util.initCount.contains(true))
                        mPresenter.show()
                    Util.initCount[3] = true
                }
                5 -> {
                    mPresenter.show()
                }
            }
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context!!)) return Util.ACTION_FAILURE
        try {
            val conn = Jsoup.parseBodyFragment(URL(address).readText()).select("div.list li")
            for (n in conn) {
                if (n.html().contains("등록된 정보가 없습니다.")) {
                    continue
                }
                if ((type == 0 || type == 4) && n.html().contains("alt=\"공지\"")) {
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
                } else if ((type == 1 || type == 5) && !n.html().contains("alt=\"공지\"")) {
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
                } else if (type == 2) {
                    val x = n.select("a").text()
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
                } else if (type == 3) {
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
                } else {
                }
            }

            try {
                Util.helper= URL("http://wiffy.io/gachon/thanks.txt ").readText()
            } catch (e: Exception) {
            }

            return Util.ACTION_SUCCESS
        } catch (e: Exception) {
            return 33
        }
    }

    override fun onPostExecute(result: Int?) {
        with(mPresenter) {
            Handler(Looper.getMainLooper()).post {
                if (result == Util.ACTION_SUCCESS) {
                    when (type) {
                        0 -> {
                            internetNotInterrupted()
                            Util.NotificationIndex = 0
                            request()
                        }
                        1 -> {
                            if (!Util.initCount.contains(false))
                                dismiss()
                            update(list)
                            Util.NotificationIndex += 1
                        }
                        2 -> {
                            if (!Util.initCount.contains(false))
                                dismiss()
                            internetNotInterrupted()
                            update(list)
                            Util.NewsIndex += 1
                        }
                        3 -> {
                            if (!Util.initCount.contains(false))
                                dismiss()
                            internetNotInterrupted()
                            update(list)
                            Util.EventIndex += 1
                        }
                        4 -> {
                            internetNotInterrupted()
                            Util.ScholarshipIndex = 0
                            request()
                        }
                        5 -> {
                            if (!Util.initCount.contains(false))
                                dismiss()
                            update(list)
                            Util.ScholarshipIndex += 1
                        }
                    }
                } else if (result == 33) {
                    dismiss()
                    list.clear()
                    update(list)
                } else {
                    dismiss()
                    internetInterrupted()
                }
            }
        }
    }
}