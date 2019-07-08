package io.wiffy.gachonNoti.ui.main.setting

interface SettingContract {
    interface View {
        fun changeView()
        fun executeTask(query:String)
        fun executeTask2(query:String)
        fun builderUp()
        fun builderDismissAndContactUp(list:ArrayList<ContactInformation>)
        fun builderDismiss()
        fun makeToast(string:String)
    }

    interface Presenter {
        fun initPresent()

    }
}