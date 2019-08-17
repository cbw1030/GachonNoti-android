package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment

interface MyInformationContract {

    abstract class View : Fragment() {
        abstract fun initView()
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun isLogin()
        abstract fun isNotLogin()
    }

    interface Presenter {
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>): Boolean
        fun themeChange(): Unit?
        fun isNotLogin()
        fun loginSetting(): Unit?
    }
}