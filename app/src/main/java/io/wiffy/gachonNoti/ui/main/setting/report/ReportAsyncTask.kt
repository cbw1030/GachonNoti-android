package io.wiffy.gachonNoti.ui.main.setting.report

import android.os.AsyncTask
import android.os.Build
import io.wiffy.gachonNoti.func.ACTION_FAILURE
import io.wiffy.gachonNoti.func.ACTION_SUCCESS
import io.wiffy.gachonNoti.func.NOT_UPDATED_YET
import io.wiffy.gachonNoti.model.Component
import io.wiffy.gachonNoti.ui.main.setting.SettingContract
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.net.URL

class ReportAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {

    private val url = "http://wiffy.io/gachon/report.php?content="

    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {
        val here =
            "$url$query(BRAND:${Build.BRAND}/MODEL:${Build.MODEL}/VERSION:${Build.VERSION.RELEASE}/SDK:${Build.VERSION.SDK_INT}/RELEASE:${Component.version})"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                URL(here).readText()
                ACTION_SUCCESS
            } catch (e: Exception) {
                e.printStackTrace()
                ACTION_FAILURE
            } catch (e: NotImplementedError) {
                NOT_UPDATED_YET
            }
        } else {
            Jsoup.connect(here).method(Connection.Method.POST).execute()
        }

        return ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        myView.builderDismiss()
        when (result) {
            ACTION_SUCCESS -> myView.toast("소중한 의견 감사드립니다!")
            ACTION_FAILURE -> myView.toast("전송에 실패하였습니다.")
            NOT_UPDATED_YET -> myView.toast("업데이트 예정입니다.")
        }
    }
}