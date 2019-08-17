package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment

interface NotificationMainContract {

    abstract class View : Fragment() {
        abstract fun initView()
        abstract fun showLoad()
        abstract fun dismissLoad(): Boolean?
        abstract fun themeChanger(bool: Boolean)
    }

    interface Presenter {
        fun search(state: Int, str: String)
        fun initPresent()
        fun fragmentInflation(list: ArrayList<Fragment?>)
        fun themeChange()
    }
}

