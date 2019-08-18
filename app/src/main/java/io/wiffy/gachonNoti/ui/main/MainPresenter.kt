package io.wiffy.gachonNoti.ui.main

import android.app.Activity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.Component
import io.wiffy.gachonNoti.ui.main.information.MyInformationFragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View, private val context: Activity) : MainContract.Presenter {

    private val mList = ArrayList<Fragment?>()

    override fun initPresent() {
        Component.state = STATE_NOTIFICATION

        val notificationFragment = NotificationFragment()
        val informationFragment = MyInformationFragment()
        val searcherFragment = SearcherFragment()
        val settingFragment = SettingFragment()

        with(mList)
        {
            add(notificationFragment)
            add(informationFragment)
            add(searcherFragment)
            add(settingFragment)
        }

        mView.changeUI(mList)
        if (!getSharedItem(Component.version, false)) {
            mView.updatedContents()
        }
    }

    override fun changeThemes() = mList.let {
        (it[STATE_NOTIFICATION] as NotificationFragment).themeChanger(true)
        (it[STATE_INFORMATION] as MyInformationFragment).themeChanger(true)
        (it[STATE_SETTING] as SettingFragment).themeChanger()
        (it[STATE_SEARCHER] as SearcherFragment).themeChanger()
    }

    override fun resetData() = (mList[STATE_SEARCHER] as SearcherFragment).resetDialog()

}