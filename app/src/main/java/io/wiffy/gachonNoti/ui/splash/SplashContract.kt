package io.wiffy.gachonNoti.ui.splash

import io.wiffy.gachonNoti.model.SuperContract

interface SplashContract {

    abstract class View : SuperContract.SuperActivity() {
        abstract fun moveToMain()
        abstract fun setImageView(id: Int)
        abstract fun setTextColor(id: Int)
        abstract fun setBirthdayText(str: String)
        abstract fun setParams()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
    }
}