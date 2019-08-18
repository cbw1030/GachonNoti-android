package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.func.ACTION_SUCCESS
import io.wiffy.gachonNoti.func.setSharedItem
import io.wiffy.gachonNoti.model.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class SearchAsyncTask(
    private val cate: String,
    private val yearSemester: String,
    private val mPresenter: SearchContract.Presenter
) :
    AsyncTask<Void, Void, Int>() {

    private var done = ""
    private var data = "searchIsuCD=00$cate"
    private val mySemester = when (Component.SEMESTER) {
        1 -> 10
        3 -> 11
        4 -> 21
        else -> 20
    }

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {

        }
    }

    override fun doInBackground(vararg params: Void?): Int {

        val type = if (Component.campus) {
            20
        } else {
            21
        }
        val url = "http://gcis.gachon.ac.kr/haksa/src/jsp/ssu/ssu1000q.jsp?"
        val url2 =
            "groupType=$type&searchYear=2019&searchTerm=$mySemester&$data&operationType=MAINSEARCH&comType=DEPT_TOT_CD&comViewValue=N&comResultTarget=cbDeptCD&condition1=CS0000&condition2=20&condition3=TOT"
        try {
            try {
                val myUrl = URL(url + url2)
                val conn = (myUrl.openConnection() as HttpURLConnection).apply {
                    requestMethod = "GET"
                    instanceFollowRedirects = true
                    HttpURLConnection.setFollowRedirects(true)
                    doOutput = true
                    doInput = true
                    useCaches = false
                    defaultUseCaches = false
                }
                val `is` = conn.inputStream
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(`is`, "euc-kr"))

                var line = reader.readLine()
                while (line != null) {
                    builder.append(line.replace("(M)", "") + "\n")
                    line = reader.readLine()
                }

                done = builder.toString()
            } catch (e: Exception) {
            }
        } catch (e: Exception) {
        }
        return ACTION_SUCCESS
    }

    @SuppressLint("ApplySharedPref")
    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            val campus = if (Component.campus) {
                "global"
            } else {
                "medical"
            }
            if (mySemester == 10 || mySemester == 20) {
                if (done.contains("<haksuNo>")) {
                    setSharedItem("$yearSemester-$cate-$campus", done)
                    mPresenter.dismissLoad()
                } else {
                    mPresenter.error()
                }
            } else {
                setSharedItem("$yearSemester-$cate-$campus", done)
                mPresenter.dismissLoad()
            }
        }
    }
}