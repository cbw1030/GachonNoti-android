package io.wiffy.gachonNoti.ui.main.notification.event

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util

class EventPresenter(val mView: EventContract.View, private val context: Context?) : EventContract.Presenter {

    private var list = ParseList()
    private var isloading = true


    override fun initPresent() {
        mView.changeUI(list)
        EventAsyncTask(list, this, context).execute()
    }

    override fun load() {
        if (!isloading) {
            request()
        }
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
        EventAsyncTask(list, this, context).execute()
    }

    override fun resetList() {
        Util.EventIndex = 0
        list.clear()
        EventAsyncTask(list, this, context).execute()
    }

    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }
}