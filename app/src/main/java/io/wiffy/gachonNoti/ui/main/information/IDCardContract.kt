package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment

interface IDCardContract {
    interface View{
        fun initView()
        fun showLoad()
        fun dismissLoad()
        fun isLogin()
        fun isNotLogin()
    }
    interface Presenter{
        fun initPresent()
        fun fragmentInflation(list:ArrayList<Fragment?>)
        fun themeChange()
        fun isNotLogin()
        fun loginSetting()
    }
}