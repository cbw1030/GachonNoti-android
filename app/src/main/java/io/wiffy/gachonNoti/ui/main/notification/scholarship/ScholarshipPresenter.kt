package io.wiffy.gachonNoti.ui.main.notification.scholarship

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class ScholarshipPresenter(private val mView: NotificationComponentContract.View, private val context: Context?) :
    NotificationComponentContract.Presenter {
    private var list = ParseList()
    private var isloading = true

    override fun resetList() {
        list.clear()
        ScholarshipAsyncTaskForN(list, this, context).execute()
    }

    override fun initPresent() {
        mView.changeUI(list)
        ScholarshipAsyncTaskForN(list, this, context).execute()
    }

    override fun request() {
        isloading = true
        ScholarshipAsyncTask(list, this, context).execute()
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


    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }
}