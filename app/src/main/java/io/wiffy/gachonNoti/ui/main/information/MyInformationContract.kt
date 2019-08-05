package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment

interface MyInformationContract {
    interface View {
        fun initView()
        fun showLoad()
        fun dismissLoad(): Boolean?
        fun isLogin()
        fun isNotLogin()
    }

    interface Presenter {
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>): Boolean
        fun themeChange(): Unit?
        fun isNotLogin()
        fun loginSetting(): Unit?
    }
}