package io.wiffy.gachonNoti.ui.main.notification

import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.SuperContract

interface NotificationContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun changeUI(list: ParseList)
        abstract fun updateUI(list: ParseList)
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun internetUnusable(): Boolean
        abstract fun internetUsable(): Boolean
        abstract fun search(str: String)
        abstract fun themeChanger(bool: Boolean)
    }

    interface Presenter : SuperContract.SuperObject {
        fun internetInterrupted(): Boolean
        fun internetNotInterrupted(): Boolean
        fun resetList()
        fun initPresent()
        fun update(data: ParseList)
        fun show()
        fun dismiss(): Boolean?
        fun request()
        fun search(src: String)
        fun setType(mType: Int)
        fun pageUp()
    }
}