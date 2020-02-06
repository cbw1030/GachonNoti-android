package io.wiffy.gachonNoti.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.SuperContract

interface MainContract {
    abstract class View : SuperContract.SuperActivity() {
        abstract  fun sendContext():Context
        abstract fun initView(mList: ArrayList<Fragment?>)
        abstract fun builderUp(): Boolean?
        abstract fun builderDismiss(): Boolean?
        abstract fun setTabText(str: String)
        abstract fun allThemeChange()
        abstract fun themeChange()
        abstract fun login()
        abstract fun logout()
        abstract fun setTitle(pair: Pair<String, Boolean>)
        abstract fun askSetPattern()
        abstract fun changePattern()
        abstract fun patternVisibility()
        abstract fun mainLogout()
        abstract fun mainLogin()
        abstract fun darkTheme()
        abstract fun linkToSite(url:String)
    }

    interface Presenter : SuperContract.SuperPresenter {
        fun changeThemes()
        fun deleteRoomData()
        fun resetTimeTable(): Unit?
        fun logout()
        fun login()
        fun mainLogChecking()
        fun patternVisibility()
        fun positiveButton()
        fun negativeButton()
    }

}