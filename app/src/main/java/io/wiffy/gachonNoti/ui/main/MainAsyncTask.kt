package io.wiffy.gachonNoti.ui.main

import android.os.AsyncTask
import io.wiffy.gachonNoti.model.Util
import org.jsoup.Jsoup
import java.net.URL

class MainAsyncTask(val list: ArrayList<String>, private val mView: MainContract.View) : AsyncTask<Void, Void, Int>() {


    override fun onPreExecute() {
        mView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {
        val address = "${Util.mobileURL1}${Util.index}${Util.mobileURL2}"
        val conn = Jsoup.parseBodyFragment(URL(address).readText())
//파싱해오기
        return 0
    }


    override fun onPostExecute(result: Int?) {
        mView.builderDismiss()
        if (Util.index == 0) {
            mView.init()
        } else {
        }
        Util.index += 1
    }
}