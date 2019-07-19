package io.wiffy.gachonNoti.ui.main.setting

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import io.wiffy.gachonNoti.model.SslConnect
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import java.io.InputStream
import java.lang.Exception
import java.net.URL
import javax.net.ssl.HttpsURLConnection

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
            val getObject =
                JSONObject(EntityUtils.toString(httpClient.execute(httpPost).entity)).getJSONObject("ds_output").apply {
                    number = getString("userUniqNo")

                    studentInformation = StudentInformation(
                        getString("userNm"),
                        number,
                        ids,
                        password,
                        getJSONArray("clubList").getJSONObject(0).getString("clubNm"),
                        "https://gcis.gachon.ac.kr/common/picture/haksa/shj/$number.jpg"
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