package io.wiffy.gachonNoti.ui.main.notification.event

import android.content.Context
import io.wiffy.gachonNoti.model.data.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentAsyncTask
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class EventPresenter(val mView: NotificationComponentContract.View, private val context: Context?) :
    NotificationComponentContract.Presenter {

    private var list = ParseList()
    private var isloading = true


    override fun initPresent() {
        mView.changeUI(list)
        NotificationComponentAsyncTask(list, this, context, 3, null).execute()
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

    override fun show() = mView.showLoad()

    override fun dismiss() = mView.dismissLoad()

    override fun request() {
        isloading = true
        NotificationComponentAsyncTask(list, this, context, 3, null).execute()
    }

    override fun resetList() {
        Util.EventIndex = 0
        list.clear()
        NotificationComponentAsyncTask(list, this, context, 3, null).execute()
    }

    override fun search(src: String) {
        list.clear()
        Util.EventIndex = 0
        NotificationComponentAsyncTask(list, this, context, 3, src).execute()
    }

    override fun internetInterrupted() = mView.internetUnusable()


    override fun internetNotInterrupted() = mView.internetUsable()
}