package io.wiffy.gachonNoti.ui.main.setting

import io.wiffy.gachonNoti.model.data.ContactInformation

interface SettingContract {
    interface View {
        fun changeView()
        fun executeTask(query:String)
        fun executeTask2(query:String)
        fun builderUp()
        fun builderDismissAndContactUp(list:ArrayList<ContactInformation>)
        fun builderDismiss()
        fun makeToast(string:String)
        fun changeCampus(bool:Boolean)
    }

    interface Presenter {
        fun initPresent()
    }
}