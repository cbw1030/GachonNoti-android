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
        abstract fun patternCheck(): Unit?
        abstract fun setPatternVisibility(): Unit?
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>): Boolean
        fun themeChange(): Unit?
        fun isNotLogin()
        fun loginSetting(): Unit?
        fun resetTable(): Unit?
        fun patternCheck(): Unit?
        fun setPatternVisibility(): Unit?
    }
}