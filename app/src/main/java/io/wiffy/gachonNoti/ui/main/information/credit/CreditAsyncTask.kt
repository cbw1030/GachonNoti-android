package io.wiffy.gachonNoti.ui.main.information.credit

import io.wiffy.gachonNoti.func.ACTION_FAILURE
import io.wiffy.gachonNoti.func.ACTION_SUCCESS
import io.wiffy.gachonNoti.func.isNetworkConnected
import io.wiffy.gachonNoti.model.CreditInformation
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.MainActivity
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import java.lang.Exception

class CreditAsyncTask(val mView: CreditContract.View, val number: String) :
    SuperContract.SuperAsyncTask<Void, Void, Int>() {
    val list = ArrayList<CreditInformation>()

    override fun onPreExecute() {
        MainActivity.mView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!isNetworkConnected(mView.sendContext()!!)) return ACTION_FAILURE
        return try {
            val page = Jsoup.parseBodyFragment(
                EntityUtils.toString(
                    DefaultHttpClient().execute(
                        HttpPost("http://smart.gachon.ac.kr:8080/WebMain?STUDENT_NO=$number&SQL_ID=mobile%2Faffairs%3AAFFAIRS_CREDIT_INFO_SQL_S01&fsp_action=AffairsAction&fsp_cmd=executeMapList&callback_page=%2Fmobile%2Fgachon%2Faffairs%2FAffCreditInfoList.jsp")
                    ).entity
                )
            ).select("tr")

            for (x in 1 until page.size) page[x].text().split(" ").let {
                try {
                    list.add(CreditInformation(it[0], it[1], it[2]).apply {
                        console("${page[x].text()}->$name/$isu/$chu")
                    })
                } catch (e: Exception) {
                    console("no")
                }

            }

            mView.initList(list)

            ACTION_SUCCESS
        } catch (e: Exception) {
            ACTION_FAILURE
        }
    }

    override fun onPostExecute(result: Int?) {
        MainActivity.mView.builderDismiss()
        if (result == ACTION_FAILURE) mView.toast("인터넷 연결을 확인해주세요.")
    }
}