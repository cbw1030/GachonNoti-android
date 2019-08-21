package io.wiffy.gachonNoti.ui.main.information.timeTable

import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.model.SuperContract
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.jsoup.Jsoup
import java.lang.Exception

class TimeTableAsyncTask(val mView: TimeTableContract.View, val number: String) :
    SuperContract.SuperAsyncTask<Void, Void, Int>() {
    override fun doInBackground(vararg params: Void?): Int {
        val sem = 20

//        when (Component.SEMESTER) {
//            1 -> "10"
//            2 -> "20"
//            3 -> "11"
//            else -> "21"
//        }
        try {
            val page = Jsoup.parseBodyFragment(
                EntityUtils.toString(
                    DefaultHttpClient().execute(
                        HttpPost("http://smart.gachon.ac.kr:8080/WebMain?YEAR=${Component.YEAR}&TERM_CD=$sem&STUDENT_NO=$number&GROUP_CD=CS&SQL_ID=mobile%2Faffairs%3ACLASS_TIME_TABLE_STUDENT_SQL_S01&fsp_action=AffairsAction&fsp_cmd=executeMapList&callback_page=%2Fmobile%2Fgachon%2Faffairs%2FAffClassTimeTableList.jsp")
                    ).entity
                )
            ).select("li")

//            console(page.html())

            for (element in page) {
                if (element.text().contains("요일")){
                    console(element.select("a").text().split("요일")[0])
                    for (data in element.select("ul.schedule_gray li")) {
//                        console(data.html())
                    }
                }
            }

        } catch (e: Exception) {
            console("ERROR")
        }
        return 0
    }
}