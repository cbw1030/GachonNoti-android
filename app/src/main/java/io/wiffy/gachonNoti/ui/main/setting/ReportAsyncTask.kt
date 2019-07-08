package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask

class ReportAsyncTask(private val myView: SettingContract.View, private val query: String):AsyncTask<Void,Void,Int>() {
    override fun onPreExecute() {
        myView.builderUp()
    }
    override fun doInBackground(vararg params: Void?): Int {

        return 0
    }

    override fun onPostExecute(result: Int?) {
        myView.builderDismiss()
        if(result==0)myView.makeToast("전송에 성공하였습니다.")
        else myView.makeToast("전송에 실패하였습니다.")
    }
}