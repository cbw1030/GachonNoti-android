package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment

interface NotificationMainContract {
    interface View{
        fun initView()
        fun inflateNotification()
        fun inflateNews()
        fun inflateEvent()
        fun inflateScholarship()
        fun showLoad()
        fun dismissLoad()
    }
    interface Presenter{
        fun initPresent()
        fun fragmentInflation(list:ArrayList<Fragment?>)
        fun themeChange()
        fun themeChange1()
        fun themeChange2()
        fun themeChange3()
        fun themeChange4()

    }
}