package io.wiffy.gachonNoti.ui.main

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

    }
    interface PresenterSetting
    {

    }
}