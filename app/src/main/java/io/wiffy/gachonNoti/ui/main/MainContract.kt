package io.wiffy.gachonNoti.ui.main

import android.view.View
import androidx.fragment.app.Fragment

interface MainContract {
    interface View{
        fun init()
        fun changeUI(mList:ArrayList<Fragment>)
        fun builderUp()
        fun builderDismiss()
        fun getList():ArrayList<String>
    }
    interface Presenter{
        fun initPresent()
    }

    interface FragmentNotification{

    }
    interface PresenterNotification{

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