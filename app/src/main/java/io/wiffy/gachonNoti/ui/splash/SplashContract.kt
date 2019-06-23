package io.wiffy.gachonNoti.ui.splash

interface SplashContract {
    interface View{
        fun changeUI()
        fun moveToMain()
        fun firstBoot()
    }
    interface Presenter{
        fun initPresent()
        fun move()
    }
}