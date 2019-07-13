package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment

interface NotificationMainContract {
    interface View{
        fun initView()
        fun showLoad()
        fun dismissLoad()
    }
    interface Presenter{
        fun initPresent()
        fun fragmentInflation(list:ArrayList<Fragment?>)
        fun themeChange()
    }
}