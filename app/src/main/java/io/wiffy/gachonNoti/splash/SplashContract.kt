package io.wiffy.gachonNoti.splash

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