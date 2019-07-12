package io.wiffy.gachonNoti.ui.main.notification.news

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util

class NewsAsyncTask(private val list: ParseList, private val mPresenter: NewsPresenter, private val context: Context?) :
    AsyncTask<Void, Void, Int>() {
    override fun onPreExecute() {
        Handler(Looper.getMainLooper()).post {
            mPresenter.show()
        }
    }

    override fun doInBackground(vararg params: Void?): Int {
        if (!Util.isNetworkConnected(context!!)) return Util.ACTION_FAILURE


        return Util.ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        Handler(Looper.getMainLooper()).post {
            if (result == Util.ACTION_SUCCESS) {
                mPresenter.dismiss()
                mPresenter.update(list)
                Util.index += 1
            } else {
                mPresenter.dismiss()
                mPresenter.internetInterrupted()
            }
        }
    }
}