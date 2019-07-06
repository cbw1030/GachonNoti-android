package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.Util
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class SearchAsyncTask(
    private val data: String,
    private val cate: String,
    private val yearSemester: String,
    private val mPresenter: SearchContract.Presenter
) :
    AsyncTask<Void, Void, Int>() {

    private var done = ""
    private val mySemester = when (Util.SEMESTER) {
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


        val url = "http://gcis.gachon.ac.kr/haksa/src/jsp/ssu/ssu1000q.jsp?"
        val url2 =
            "groupType=20&searchYear=2019&searchTerm=$mySemester&$data&operationType=MAINSEARCH&comType=DEPT_TOT_CD&comViewValue=N&comResultTarget=cbDeptCD&condition1=CS0000&condition2=20&condition3=TOT"
        try {
            try {
                val myUrl = URL(url + url2)
                val conn = myUrl.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.instanceFollowRedirects = true
                HttpURLConnection.setFollowRedirects(true)

                conn.doOutput = true
                conn.doInput = true
                conn.useCaches = false
                conn.defaultUseCaches = false
                val `is` = conn.inputStream
                val builder = StringBuilder()
                val reader = BufferedReader(InputStreamReader(`is`, "euc-kr"))

                var line = reader.readLine()
                while (line != null) {
                    builder.append(line + "\n")
                    line = reader.readLine()
                }

                done = builder.toString()
            } catch (e: Exception) {
            }
        } catch (e: Exception) {
        }
        return 0
    }

    @SuppressLint("ApplySharedPref")
    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (mySemester == 10 || mySemester == 20) {
                if (done.contains("<haksuNo>")) {
                    Util.sharedPreferences.edit().putString("$yearSemester-$cate", done).commit()
                    mPresenter.dismissLoad()
                } else {
                    mPresenter.error()
                }
            } else {
                Util.sharedPreferences.edit().putString("$yearSemester-$cate", done).commit()
                mPresenter.dismissLoad()
            }
        }
    }
}