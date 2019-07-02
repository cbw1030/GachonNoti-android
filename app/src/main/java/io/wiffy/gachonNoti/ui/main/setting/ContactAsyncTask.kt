package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.lang.Exception
import java.net.URL

class ContactAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {
    val myList = ArrayList<ContactInformation>()

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
                    myList.add(
                        ContactInformation(
                            rows[n].select("td")[0].text(),
                            rows[n].select("td")[2].text(),
                            rows[n].select("td")[3].text()
                        )
                    )
                } catch (e: Exception) {
                }
            }
        return 0
    }

    override fun onPostExecute(result: Int?) {

        myView.builderDismissAndContactUp(myList)
    }
}