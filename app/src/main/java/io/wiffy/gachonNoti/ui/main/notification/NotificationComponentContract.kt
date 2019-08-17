package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.ParseList

interface NotificationComponentContract {
    abstract class View : Fragment() {
        abstract fun changeUI(list: ParseList)
        abstract fun updateUI(list: ParseList)
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun internetUnusable(): Boolean
        abstract fun internetUsable(): Boolean
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