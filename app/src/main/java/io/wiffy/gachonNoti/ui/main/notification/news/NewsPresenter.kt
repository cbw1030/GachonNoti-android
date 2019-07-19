package io.wiffy.gachonNoti.ui.main.notification.news

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class NewsPresenter(val mView: NotificationComponentContract.View, private val context: Context?) : NotificationComponentContract.Presenter {

    private var list = ParseList()
    private var isloading = true

    override fun initPresent() {
        mView.changeUI(list)
        NewsAsyncTask(list,this,context).execute()
    }

    override fun load() {
        if (!isloading) {
            request()
        }
    }

    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }

    override fun update(data: ParseList) {
        isloading = false
        list = data
        mView.updateUI(list)
    }

    override fun show() {
        mView.showLoad()
    }

    override fun dismiss() {
        mView.dismissLoad()
    }


    override fun request() {
        isloading = true
        NewsAsyncTask(list,this,context).execute()
    }

    override fun resetList() {
        Util.NewsIndex = 0
        list.clear()
        NewsAsyncTask(list,this,context).execute()
    }
}