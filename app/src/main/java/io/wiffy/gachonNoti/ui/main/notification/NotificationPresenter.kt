package io.wiffy.gachonNoti.ui.main.notification

import io.wiffy.gachonNoti.model.ParseList

class NotificationPresenter(private val mView: NotificationContract.View) :
    NotificationContract.Presenter {

    private var list = ParseList()
    private var isLoading = false
    private var type = 0
    private var page = 0
    private var searchKey = ""

    override fun initPresent() {
        mView.changeUI(list)
        resetList()
    }

    override fun pageUp() {
        page += 1
        request()
    }

    override fun search(str: String) {
        searchKey = str
        page = 0
        list.clear()
        request()
    }

    override fun setType(mType: Int) {
        type = mType
        resetList()
    }

    override fun resetList() {
        mView.recyclerViewClear()
        searchKey = ""
        page = 0
        list.clear()
        request()
    }

    override fun update(data: ParseList) {
        isLoading = false
        list = data
        mView.updateUI(list)
    }

    override fun request() {
        if (!isLoading) {
            isLoading = true
            NotificationAsyncTask(list, this, type, searchKey, page).execute()
        }
    }

    override fun getContext() = mView.sendContext()

    override fun show() = mView.showLoad()

    override fun dismiss() = mView.dismissLoad()

    override fun internetInterrupted() = mView.internetUnusable()

    override fun internetNotInterrupted() = mView.internetUsable()

}