package io.wiffy.gachonNoti.ui.main.setting

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import java.lang.Exception

class LoginAsyncTask(val ids: String, val password: String, val context: Context, val mView:DetailDialog) : AsyncTask<Void, Void, Int>() {

    lateinit var studentInformation: StudentInformation

    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            MainActivity.mView.builderUp()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context)) return Util.ACTION_FAILURE
        try {
//            TODO()
            studentInformation = StudentInformation("박상현", "201735829", ids, password)
        }catch (e:Exception){
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