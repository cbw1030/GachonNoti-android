package io.wiffy.gachonNoti.ui.main.setting

import android.os.AsyncTask
import io.wiffy.gachonNoti.model.Util

class ReportAsyncTask(private val myView: SettingContract.View, private val query: String) :
    AsyncTask<Void, Void, Int>() {

    private val url = "http://wiffy.io/gachon/report?content="

    override fun onPreExecute() {
        myView.builderUp()
    }

    override fun doInBackground(vararg params: Void?): Int =
        try {
            TODO(
                "query는 버그리포트:내용 혹은 개선사항:내용으로 이루어짐" +
                        "여기만 추가하면 끝!"
            )
            Util.ACTION_SUCCESS
        } catch (e: Exception) {
            Util.ACTION_FAILURE
        } catch (e: NotImplementedError) {
            // TODO하면 여기로 빠짐
            Util.NOT_UPDATED_YET
        }


    override fun onPostExecute(result: Int?) {
        myView.builderDismiss()
        when (result) {
            Util.ACTION_SUCCESS -> myView.makeToast("전송에 성공하였습니다.")
            Util.ACTION_FAILURE -> myView.makeToast("전송에 실패하였습니다.")
            Util.NOT_UPDATED_YET -> myView.makeToast("업데이트 예정입니다.")
        }
    }
}