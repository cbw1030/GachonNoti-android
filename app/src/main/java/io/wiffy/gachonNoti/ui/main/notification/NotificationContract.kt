package io.wiffy.gachonNoti.ui.main.notification

interface NotificationContract {
    interface View {
        fun changeUI(list: ParseList)
        fun updateUI(list: ParseList)
        fun showLoad()
        fun dismissLoad()
    }

    interface Presenter {
        fun resetList()
        fun uno()
        fun initPresent()
        fun load()
        fun update(data: ParseList)
        fun show()
        fun dismiss()
        fun request()
    }
}