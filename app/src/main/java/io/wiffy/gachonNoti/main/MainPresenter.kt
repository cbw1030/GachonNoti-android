package io.wiffy.gachonNoti.main

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.main.notification.NotificationFragment
import io.wiffy.gachonNoti.main.setting.SettingFragment

class MainPresenter(private val mView:MainContract.View):MainContract.Presenter {
    private val mList = ArrayList<Fragment>()

    override fun initPresent() {
        mList.add(NotificationFragment())
        mList.add(SettingFragment())

        mView.changeUI(mList)
    }
}