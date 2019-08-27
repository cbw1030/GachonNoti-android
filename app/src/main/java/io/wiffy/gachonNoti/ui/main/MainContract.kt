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
        abstract fun login()
        abstract fun logout()
        abstract fun setTitle(pair: Pair<String, Boolean>)
        abstract fun askSetPattern()
        abstract fun changePattern()
        abstract fun checkPattern(): Unit?
        abstract fun patternVisibility()
        abstract fun changeStatusBar(bool: Boolean)
        abstract fun mainLogout()
        abstract fun mainLogin()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
        fun changeThemes()
        fun deleteRoomData()
        fun resetTimeTable(): Unit?
        fun logout()
        fun login()
        fun mainLogChecking()
        fun checkPattern(): Unit?
        fun patternVisibility()
        fun positiveButton()
        fun negativeButton()
    }

}