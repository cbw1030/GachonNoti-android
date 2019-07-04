package io.wiffy.gachonNoti.ui.webView

import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import org.jsoup.Jsoup
import java.net.URL


class WebAsyncTask(
    private val url: String,
    private val mPresenter: WebViewContract.Presenter,
    private var redirect: Boolean
) :
    AsyncTask<Void, Void, Int>() {

    private var javaS = ""
    private var goHref = ""

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.builderUp()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        val address = when (redirect) {
            true -> url
            false -> {
                val newSeq = url.split("boardType_seq=")[1].split("&")[0]
                val newNo = url.split("board_no=")[1].split("&")[0]
                "http://www.gachon.ac.kr/community/opencampus/03.jsp?mode=view&boardType_seq=$newSeq&board_no=$newNo"
            }
        }
        val doc = Jsoup.parseBodyFragment(URL(address).readText())
        for (div in doc.select("div")) {
            if (div.hasClass("boardview")) {
                for (td in div.select("td")) {
                    if (td.hasClass("td")) {
                        val tmp = td.html()
                        if (tmp.contains("첨부")) {
                            javaS = "$javaS$tmp<hr>"
                        }
                    }
                    if (td.hasClass("text")) {
                        javaS = "$javaS${td.html()}"
                        if (javaS.contains("<script>document.location.href=\"")) {
                            goHref = javaS.split("<script>document.location.href=\"")[1].split("\";</script>")[0]
                        } else {
                            javaS = javaS.replace("src=\"/", "src=\"http://www.gachon.ac.kr/")
                            javaS = javaS.replace("href=\"/", "href=\"http://www.gachon.ac.kr/")
                            Handler(Looper.getMainLooper()).post {
                                mPresenter.updateWeb(javaS)
                                mPresenter.builderDismiss()
                            }
                        }
                    }
                }
                break
            }
        }
        return 0
    }


    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (goHref.contains("http://")) {
                Log.d("asdf", goHref)
                WebAsyncTask(goHref, mPresenter, true).execute()
            }
        }
    }
}