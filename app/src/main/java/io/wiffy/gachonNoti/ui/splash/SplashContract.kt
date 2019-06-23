package io.wiffy.gachonNoti.ui.splash

interface SplashContract {
    interface View{
        fun changeUI()
        fun moveToMain()
    }
    interface Presenter{
        fun initPresent()
        fun move()
    }
}