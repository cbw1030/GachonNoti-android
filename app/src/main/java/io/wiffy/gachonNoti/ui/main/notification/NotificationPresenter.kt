package io.wiffy.gachonNoti.ui.main.notification


class NotificationPresenter(private val mView: NotificationContract.View) : NotificationContract.Presenter {

    private var list = ParseList()
    private var isloading = true


    override fun initPresent() {
        NotiAsyncTaskForN(list, this).execute()
        mView.changeUI(list)
    }

    override fun load() {
        if (!isloading) {
            request()
        }
    }

    override fun resetList() {
        list.clear()
        NotiAsyncTaskForN(list, this).execute()
    }

    override fun uno() {
        mView.changeUI(list)
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
        NotiAsyncTask(list, this).execute()
    }

    override fun internetInterrupted() {
        mView.internetUnusable()
    }

    override fun internetNotInterrupted() {
        mView.internetUsable()
    }
}