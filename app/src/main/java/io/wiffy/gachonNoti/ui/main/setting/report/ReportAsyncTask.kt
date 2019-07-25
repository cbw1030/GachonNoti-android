package io.wiffy.gachonNoti.ui.main.setting.report

import android.os.AsyncTask
import android.os.Build
import android.util.Log
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.setting.SettingContract
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL

class ReportAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {

    private val url = "http://wiffy.io/gachon/report.php?content="

    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {
        val here =
            "$url$query(BRAND:${Build.BRAND}/MODEL:${Build.MODEL}/VERSION:${Build.VERSION.RELEASE}/SDK:${Build.VERSION.SDK_INT}/RELEASE:${Util.version})"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                URL(here).readText()
                Util.ACTION_SUCCESS
            } catch (e: Exception) {
                e.printStackTrace()
                Util.ACTION_FAILURE
            } catch (e: NotImplementedError) {
                Util.NOT_UPDATED_YET
            }
        } else {
            val x =
                Jsoup.connect(here).method(Connection.Method.POST).execute()
        }

        return Util.ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        myView.builderDismiss()
        when (result) {
            Util.ACTION_SUCCESS -> myView.makeToast("소중한 의견 감사드립니다!")
            Util.ACTION_FAILURE -> myView.makeToast("전송에 실패하였습니다.")
            Util.NOT_UPDATED_YET -> myView.makeToast("업데이트 예정입니다.")
        }
    }
}