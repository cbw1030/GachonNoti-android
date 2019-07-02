package io.wiffy.gachonNoti.ui.main.setting

interface SettingContract {
    interface View {
        fun changeView()
    }

    interface Presenter {
        fun initPresent()

    }
}