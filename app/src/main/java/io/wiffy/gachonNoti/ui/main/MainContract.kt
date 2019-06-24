package io.wiffy.gachonNoti.ui.main

import android.view.View
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.notification.Parse
import io.wiffy.gachonNoti.ui.main.notification.ParseList

interface MainContract {
    interface View{
        fun init()
        fun changeUI(mList:ArrayList<Fragment>)
        fun builderUp()
        fun builderDismiss()
        fun getList():ParseList
        fun makeToast(str:String)
    }
    interface Presenter{
        fun initPresent()
    }

    interface FragmentNotification{
        fun changeUI()
    }
    interface PresenterNotification{
        fun initPresent()
    }

    interface FragmentSetting{

    }
    interface PresenterSetting
    {
        fun initPresent()

    }
}