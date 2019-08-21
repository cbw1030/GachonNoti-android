package io.wiffy.gachonNoti.ui.main.notification

import io.wiffy.gachonNoti.func.STATE_NOTIFICATION
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.ui.main.MainActivity

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
        Component.titles[STATE_NOTIFICATION] = Pair(
            when (type) {
                1 -> "가천뉴스"
                2 -> "행사소식"
                3 -> "장학소식"
                else -> "공지사항"
            }, false
        )
        MainActivity.mView.setTitle(
            Pair(
                Component.titles[STATE_NOTIFICATION].first,
                Component.titles[STATE_NOTIFICATION].second
            )
        )
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