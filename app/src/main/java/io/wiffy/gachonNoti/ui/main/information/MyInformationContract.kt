package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.SuperContract

interface MyInformationContract {

    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
        abstract fun isLogin()
        abstract fun isNotLogin()
        abstract fun setPatternVisibility(): Unit?
    }

    interface Presenter : SuperContract.SuperPresenter {
        fun fragmentInflation(list: ArrayList<Fragment?>): Boolean
        fun themeChange(): Unit?
        fun isNotLogin()
        fun loginSetting(): Unit?
        fun resetTable(): Unit?
        fun setPatternVisibility(): Unit?
    }
}