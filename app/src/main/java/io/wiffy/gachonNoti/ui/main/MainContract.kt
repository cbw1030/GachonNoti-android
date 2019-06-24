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
        fun addListener(listener1:android.view.View.OnClickListener)
        fun languageSetting()
    }
    interface PresenterSetting
    {
        fun initPresent()

    }
}