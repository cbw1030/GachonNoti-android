package io.wiffy.gachonNoti.ui.main.notification

import io.wiffy.gachonNoti.model.data.ParseList

interface NotificationComponentContract {
    interface View {
        fun changeUI(list: ParseList)
        fun updateUI(list: ParseList)
        fun showLoad()
        fun dismissLoad(): Boolean?
        fun internetUnusable(): Boolean
        fun internetUsable(): Boolean
    }

    interface Presenter {
        fun internetInterrupted(): Boolean
        fun internetNotInterrupted(): Boolean
        fun resetList()
        fun initPresent()
        fun load()
        fun update(data: ParseList)
        fun show()
        fun dismiss(): Boolean?
        fun request()
        fun search(src: String)
    }
}