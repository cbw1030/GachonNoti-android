package io.wiffy.gachonNoti.ui.main.setting


import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.model.SuperContract


interface SettingContract {

    abstract class View : SuperContract.SuperFragment() {
        abstract fun changeView()
        abstract fun adminView()
        abstract fun adminLogout()
        abstract fun adminLogin()
        abstract fun executeTask(query: String, query2: Boolean, query3: Boolean)
        abstract fun executeTask2(query: String)
        abstract fun builderUp()
        abstract fun builderDismissAndContactUp(list: ArrayList<ContactInformation>)
        abstract fun builderDismiss(): Boolean
        abstract fun changeCampus(bool: Boolean)
        abstract fun setSwitch(bool: Boolean)
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
        fun setOn()
        fun setAdminLogin()
        fun setAdminLogout()
        fun setOff()
    }
}