package io.wiffy.gachonNoti.ui.main.information.idCard

import androidx.fragment.app.Fragment

interface IDCardContract {

    abstract class View : Fragment() {
        abstract fun initView()
    }

    interface Presenter {
        fun initPresent()
    }
}