package io.wiffy.gachonNoti.ui.main.setting

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.ContactInformation


interface SettingContract {

    abstract class View : Fragment() {
        abstract fun changeView()
        abstract fun executeTask(query: String)
        abstract fun executeTask2(query: String)
        abstract fun builderUp()
        abstract fun builderDismissAndContactUp(list: ArrayList<ContactInformation>)
        abstract fun builderDismiss(): Boolean
        abstract fun makeToast(string: String)
        abstract fun changeCampus(bool: Boolean)
    }

    interface Presenter {
        fun initPresent()
    }
}