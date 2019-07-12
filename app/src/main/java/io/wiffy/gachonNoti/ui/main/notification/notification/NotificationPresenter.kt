package io.wiffy.gachonNoti.ui.main.notification.notification

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList

class NotificationPresenter(private val mView: NotificationContract.View, private val context: Context?) :
    NotificationContract.Presenter {

    private var list = ParseList()
    private var isloading = true


    override fun initPresent() {
        mView.changeUI(list)
        NotificationAsyncTaskForN(list, this, context).execute()
    }

    override fun load() {
        if (!isloading) {
            request()
        }
    }

    override fun resetList() {
        list.clear()
        NotificationAsyncTaskForN(list, this, context).execute()
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
        NotificationAsyncTask(list, this, context).execute()
    }

    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }
}