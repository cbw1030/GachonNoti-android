package io.wiffy.gachonNoti.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    private val mList = ArrayList<Fragment>()

    override fun initPresent() {
        val notificationFragment = NotificationFragment()
        mList.add(notificationFragment)
        mList.add(SettingFragment())
        mView.changeUI(mList)
    }

    override fun changeThemes() {
        (mList[1] as SettingFragment).themeChanger()
    }
}