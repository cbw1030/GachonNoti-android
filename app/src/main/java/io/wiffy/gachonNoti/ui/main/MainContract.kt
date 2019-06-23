package io.wiffy.gachonNoti.ui.main

import android.view.View
import androidx.fragment.app.Fragment

interface MainContract {
    interface View{
        fun changeUI(mList:ArrayList<Fragment>)
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