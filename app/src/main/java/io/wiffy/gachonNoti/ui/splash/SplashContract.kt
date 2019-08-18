package io.wiffy.gachonNoti.ui.splash

import androidx.appcompat.app.AppCompatActivity
import io.wiffy.gachonNoti.model.SuperContract

interface SplashContract {

    abstract class View : SuperContract.SuperActivity() {
        abstract fun moveToMain()
        abstract fun subscribe()
    }

    interface Presenter : SuperContract.SuperObject {
        fun initPresent()
    }
}