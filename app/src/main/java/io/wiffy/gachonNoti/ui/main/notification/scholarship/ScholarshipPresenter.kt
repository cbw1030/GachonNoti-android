package io.wiffy.gachonNoti.ui.main.notification.scholarship

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentAsyncTask
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class ScholarshipPresenter(private val mView: NotificationComponentContract.View, private val context: Context?) :
    NotificationComponentContract.Presenter {
    private var list = ParseList()
    private var isloading = true

    override fun resetList() {
        list.clear()
        NotificationComponentAsyncTask(list, this, context, 4, null).execute()
    }

    override fun initPresent() {
        mView.changeUI(list)
        NotificationComponentAsyncTask(list, this, context, 4, null).execute()
    }

    override fun search(src: String) {
        Util.ScholarshipIndex = 0
        list.clear()
        NotificationComponentAsyncTask(list, this, context, 5, src).execute()
    }

    override fun request() {
        isloading = true
        NotificationComponentAsyncTask(list, this, context, 5, null).execute()
    }


    override fun load() {
        if (!isloading) {
            request()
        }
    }

    override fun update(data: ParseList) {
        isloading = false
        list = data
//        if(list.empty())
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