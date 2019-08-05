package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment

interface NotificationMainContract {
    interface View {
        fun initView()
        fun showLoad()
        fun dismissLoad(): Boolean?
    }

    interface Presenter {
        fun search(state: Int, str: String)
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>)
        fun themeChange()
    }
}