package io.wiffy.gachonNoti.ui.main.notification.event

import io.wiffy.gachonNoti.model.ParseList

interface EventContract {
    interface View {
        fun changeUI(list: ParseList)
        fun updateUI(list: ParseList)
        fun showLoad()
        fun dismissLoad()
        fun internetUnusable()
        fun internetUsable()
    }

    interface Presenter {
        fun internetInterrupted()
        fun internetNotInterrupted()
        fun resetList()
        fun initPresent()
        fun load()
        fun update(data: ParseList)
        fun show()
        fun dismiss()
        fun request()
    }
}