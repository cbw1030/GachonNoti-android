package io.wiffy.gachonNoti.ui.splash

import io.wiffy.gachonNoti.model.SuperContract

interface SplashContract {

    abstract class View : SuperContract.SuperActivity() {
        abstract fun moveToMain()
        abstract fun subscribe()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
    }
}