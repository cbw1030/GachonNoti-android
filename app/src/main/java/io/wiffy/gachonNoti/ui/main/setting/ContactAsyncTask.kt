package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask
import android.util.Log
import io.wiffy.gachonNoti.model.Util
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.Exception
import java.net.URL

class ContactAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {
    private val myList = ArrayList<ContactInformation>()

    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {
        val mElements = Jsoup.parseBodyFragment(URL(query).readText()).select("div.boardlist table.data")
        val rows = mElements.select("tr")

        if (rows.size > 1)
            for (n in 1 until rows.size) {
                try {
                    Log.d("asdf", rows[n].select("td")[2].text())
                    val tel = rows[n].select("td")[3].text()

                    myList.add(
                        ContactInformation(
                            rows[n].select("td")[0].text(),
                            rows[n].select("td")[2].text(),
                            when (tel.length) {
                                10 -> "${tel.substring(0,3)}-${tel.substring(3,6)}-${tel.substring(6)}"
                                else -> tel
                            }
                        )
                    )
                } catch (e: Exception) {
                }
            }
        return Util.ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {

        myView.builderDismissAndContactUp(myList)
    }
}