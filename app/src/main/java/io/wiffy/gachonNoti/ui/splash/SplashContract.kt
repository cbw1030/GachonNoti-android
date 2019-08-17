package io.wiffy.gachonNoti.ui.splash

import androidx.appcompat.app.AppCompatActivity

interface SplashContract {

    abstract class View : AppCompatActivity() {
        abstract fun changeUI(): Boolean
        abstract fun moveToMain()
        abstract fun subscribe()
    }

    interface Presenter {
        fun initPresent()
        fun move(): Boolean
    }
}