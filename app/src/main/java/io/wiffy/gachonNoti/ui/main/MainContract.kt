package io.wiffy.gachonNoti.ui.main


import android.app.AlertDialog
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.SuperContract

interface MainContract {
    abstract class View : SuperContract.SuperActivity() {
        abstract fun changeUI(mList: ArrayList<Fragment?>)
        abstract fun builderUp()
        abstract fun builderDismiss(): Boolean?
        abstract fun updatedContents()
        abstract fun setTabText(str: String)
        abstract fun allThemeChange()
        abstract fun themeChange()
        abstract fun logout()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
        fun changeThemes()
        fun resetData(): AlertDialog
        fun logout()
    }

}