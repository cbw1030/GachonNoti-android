package io.wiffy.gachonNoti.ui.main.setting.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.data.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
class LoginAsyncTask(
    private val ids: String,
    private val password: String,
   val context: Context,
    val mView: LoginDialog
) : AsyncTask<Void, Void, Int>() {

    lateinit var studentInformation: StudentInformation

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            MainActivity.mView.builderUp()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context)) return Util.ACTION_FAILURE
        val number: String
        try {
            val sendObject = JSONObject().apply {
                put("fsp_cmd", "login")
                put("DVIC_ID", "dwFraM1pVhl6mMn4npgL2dtZw7pZxw2lo2uqpm1yuMs=")
                put("fsp_action", "UserAction")
                put("LOGIN_ID", ids)
                put("USER_ID", ids)
                put("PWD", password)
                put("APPS_ID", "com.sz.Atwee.gachon")
            }
            val httpClient = DefaultHttpClient()
            val httpPost = HttpPost("http://smart.gachon.ac.kr:8080//WebJSON")
            httpPost.entity = StringEntity(sendObject.toString())
                JSONObject(EntityUtils.toString(httpClient.execute(httpPost).entity)).getJSONObject("ds_output").apply {
                    number = getString("userUniqNo")
                    studentInformation = StudentInformation(
                        getString("userNm"),
                        number,
                        ids,
                        password,
                        getJSONArray("clubList").getJSONObject(0).getString("clubNm"),
                        "http://gcis.gachon.ac.kr/common/picture/haksa/shj/$number.jpg"
                    )
                }


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