package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment

interface InformationContract {
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