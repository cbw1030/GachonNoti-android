package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask

class ContactAsyncTask(private val myView: SettingContract.View) : AsyncTask<Void, Void, Int>() {
    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int {

        return 0
    }

    override fun onPostExecute(result: Int?) {
        
        myView.builderDismissAndContactUp()
    }
}