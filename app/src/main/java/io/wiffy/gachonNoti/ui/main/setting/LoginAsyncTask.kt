package io.wiffy.gachonNoti.ui.main.setting

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class LoginAsyncTask(
    private val ids: String,
    private val password: String,
    val context: Context,
    val mView: DetailDialog
) : AsyncTask<Void, Void, Int>() {

    lateinit var studentInformation: StudentInformation
    val ids_encoded = Util.getBase64Encode(ids)
    val password_encoded = Util.getBase64Encode(password)

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            MainActivity.mView.builderUp()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context)) return Util.ACTION_FAILURE
        try {
            TODO()
            val x =HashMap<String,String>()
            x.put("user_id","okpsh0033")
            x.put("password","Ok00330246!@")

            val loginPageResponse =
                Jsoup.connect("http://www.gachon.ac.kr/inc/_mobile_login_action.jsp")
                    .timeout(3000)
                    .header("Referer", "http://m.gachon.ac.kr/site/login.jsp")
                    .header(
                        "Accept",
                        "image/gif,image/jpeg,image/pjpeg,application/x-ms-application,application/xaml+xml,application/x-ms-xbap,*/*"
                    )
                    .data(x)
                    .header("Accept-Language", "ko-KR")
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Host", "www.gachon.ac.kr")
                    .header("Connection", "Keep-Alive")
                    .userAgent("Mozilla")
                    .method(Connection.Method.POST)
                    .execute()

            val loginTryCookie = loginPageResponse.cookies()
            val loginPageDocument = loginPageResponse.parse()

            studentInformation = StudentInformation("박상현", "201735829", ids, password)
        } catch (e: Exception) {
            return Util.ACTION_FAILURE
        }
        return Util.ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            MainActivity.mView.builderDismiss()
        }
        when (result ?: Util.ACTION_FAILURE) {
            Util.ACTION_SUCCESS -> {
                mView.saveInformation(studentInformation)
            }
            else -> {
                mView.loginFailed()
            }
        }
    }
}