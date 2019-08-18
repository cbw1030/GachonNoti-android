package io.wiffy.gachonNoti.ui.main.notification

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList

class NotificationPresenter(private val mView: NotificationContract.View, private val context: Context?) :
    NotificationContract.Presenter {

    private var list = ParseList()
    private var isloading = false
    private var type = 0
    private var page = 0
    private var searchkey = ""

    override fun initPresent() {
        mView.changeUI(list)
        resetList()
    }

    override fun pageUp(){
        page += 1
        request()
    }

    override fun search(str:String){
        searchkey = str
        page = 0
        list.clear()
        request()
    }

    override fun setType(mType:Int){
        type = mType
        resetList()
    }

    override fun resetList() {
        searchkey = ""
        page = 0
        list.clear()
        request()
    }

    override fun update(data: ParseList) {
        isloading = false
        list = data
        mView.updateUI(list)
    }

    override fun request() {
        if (!isloading) {
            isloading = true
            NotificationAsyncTask(list, this, context, type, searchkey, page).execute()
        }
    }

    override fun show() = mView.showLoad()

    override fun dismiss() = mView.dismissLoad()

    override fun internetInterrupted() = mView.internetUnusable()

    override fun internetNotInterrupted() = mView.internetUsable()

}