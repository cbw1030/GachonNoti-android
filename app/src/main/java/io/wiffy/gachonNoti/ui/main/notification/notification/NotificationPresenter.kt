package io.wiffy.gachonNoti.ui.main.notification.notification

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentAsyncTask
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class NotificationPresenter(private val mView: NotificationComponentContract.View, private val context: Context?) :
    NotificationComponentContract.Presenter {

    private var list = ParseList()
    private var isloading = true


    override fun initPresent() {
        mView.changeUI(list)
        NotificationComponentAsyncTask(list,this,context,0,null).execute()
    }

    override fun load() {
        if (!isloading) {
            request()
        }
    }

    override fun resetList() {
        list.clear()
        NotificationComponentAsyncTask(list,this,context,0,null).execute()
    }


    override fun update(data: ParseList) {
        isloading = false
        list = data
        mView.updateUI(list)
    }

    override fun search(src: String) {
        Util.NotificationIndex = 0
        list.clear()
        NotificationComponentAsyncTask(list,this,context,1,src).execute()
    }
    override fun show() {
        mView.showLoad()
    }

    override fun dismiss() {
        mView.dismissLoad()
    }

    override fun request() {
        isloading = true
        NotificationComponentAsyncTask(list,this,context,1,null).execute()
    }

    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }
}