package io.wiffy.gachonNoti.ui.main.notification.news

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList

class NewsPresenter(val mView: NewsContract.View, private val context: Context?) : NewsContract.Presenter {

    private var list = ParseList()
    private var isloading = true

    override fun initPresent() {
        mView.changeUI(list)
        //asynctask
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
        // async
    }

    override fun resetList() {
        list.clear()
        //async
    }
}