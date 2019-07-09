package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask
import io.wiffy.gachonNoti.model.Util
import java.net.URL

class ReportAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {

    private val url = "http://wiffy.io/gachon/report.php?content="

    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int =
        try {
            val v = URL("$url$query").readText()
            Util.ACTION_SUCCESS
        } catch (e: Exception) {
            Util.ACTION_FAILURE
        } catch (e: NotImplementedError) {
            Util.NOT_UPDATED_YET
        }


    override fun onPostExecute(result: Int?) {
        myView.builderDismiss()
        when (result) {
            Util.ACTION_SUCCESS -> myView.makeToast("전송에 성공하였습니다.")
            Util.ACTION_FAILURE -> myView.makeToast("전송에 실패하였습니다.")
            Util.NOT_UPDATED_YET -> myView.makeToast("업데이트 예정입니다.")
        }
    }
}