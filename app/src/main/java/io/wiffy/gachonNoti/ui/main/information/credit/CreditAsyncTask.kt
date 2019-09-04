package io.wiffy.gachonNoti.ui.main.information.credit

import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.ACTION_FAILURE
import io.wiffy.gachonNoti.function.ACTION_SUCCESS
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.function.isNetworkConnected
import io.wiffy.gachonNoti.model.CreditInformation
import io.wiffy.gachonNoti.model.SuperContract
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.json.JSONObject
import org.jsoup.Jsoup
import java.lang.Exception

class CreditAsyncTask(val mView: CreditContract.View, val number: String) :
    SuperContract.SuperAsyncTask<Void, Void, Int>() {
    val list = ArrayList<CreditInformation>()

    override fun onPreExecute() {
        Component.getBuilder()?.show()
    }

    private fun getJson() =
        JSONObject("{\"DEPT_CD\":\"${getSharedItem<String>("clubCD")}\",\"fsp_ds_cmd\":[{\"TYPE\":\"N\",\"SQL_ID\":\"mobile/common:DEPT_INFO_SQL_S01\",\"INSERT_SQL_ID\":\"\",\"UPDATE_SQL_ID\":\"\",\"DELETE_SQL_ID\":\"\",\"SAVE_FLAG_COLUMN\":\"\",\"KEY_ZERO_LEN\":\"\",\"USE_INPUT\":\"N\",\"USE_ORDER\":\"Y\",\"EXEC\":\"\",\"FAIL\":\"\",\"FAIL_MSG\":\"\",\"EXEC_CNT\":0,\"MSG\":\"\"}],\"fsp_action\":\"xDefaultAction\",\"fsp_cmd\":\"execute\"}")

    override fun doInBackground(vararg params: Void?): Int {
        if (!isNetworkConnected(mView.sendContext()!!)){
            return ACTION_FAILURE
        }
        return try {
            val page = Jsoup.parseBodyFragment(
                EntityUtils.toString(
                    DefaultHttpClient().execute(
                        HttpPost("http://smart.gachon.ac.kr:8080/WebMain?STUDENT_NO=$number&SQL_ID=mobile%2Faffairs%3AAFFAIRS_CREDIT_INFO_SQL_S01&fsp_action=AffairsAction&fsp_cmd=executeMapList&callback_page=%2Fmobile%2Fgachon%2Faffairs%2FAffCreditInfoList.jsp")
                    ).entity
                )
            ).select("tr")

            for (x in 1 until page.size) {
                page[x].text().split(" ").let {
                    try {
                        list.add(CreditInformation(it[0], it[1], it[2]))
                    } catch (e: Exception) {
                    }
                }
            }

            mView.initList(
                list,
                JSONObject(EntityUtils.toString(DefaultHttpClient().execute(HttpPost("http://smart.gachon.ac.kr:8080//WebJSON").apply {
                    entity = StringEntity(getJson().toString())
                }).entity)).getJSONArray("ds_dept_info").optJSONObject(0).getString("PRNT_DEPT_NM")
            )

            ACTION_SUCCESS
        } catch (e: Exception) {
            ACTION_FAILURE
        }
    }

    override fun onPostExecute(result: Int?) {
        Component.getBuilder()?.dismiss()
        if (result == ACTION_FAILURE) {
            mView.toast("인터넷 연결을 확인해주세요.")
        }
    }
}