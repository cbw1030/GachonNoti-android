package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.SuperContract

interface MyInformationContract {

    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun isLogin()
        abstract fun isNotLogin()
    }

    interface Presenter : SuperContract.SuperObject {
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>): Boolean
        fun themeChange(): Unit?
        fun isNotLogin()
        fun loginSetting(): Unit?
    }
}