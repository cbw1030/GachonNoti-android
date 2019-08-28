package io.wiffy.gachonNoti.ui.main.notification

import android.content.Context
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.SuperContract

interface NotificationContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun changeUI(list: ParseList)
        abstract fun updateUI(list: ParseList)
        abstract fun internetUnusable(): Boolean
        abstract fun internetUsable(): Boolean
        abstract fun search(str: String)
        abstract fun themeChanger(bool: Boolean)
        abstract fun sendContext(): Context?
        abstract fun recyclerViewClear()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun internetInterrupted(): Boolean
        fun internetNotInterrupted(): Boolean
        fun resetList()
        fun initPresent()
        fun update(data: ParseList)
        fun request()
        fun search(src: String)
        fun setType(mType: Int)
        fun pageUp()
        fun getContext(): Context?
    }
}