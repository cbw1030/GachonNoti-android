package io.wiffy.gachonNoti.ui.main

import android.app.Activity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.ui.main.information.MyInformationFragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View, private val context: Activity) : MainContract.Presenter {

    private val mList = ArrayList<Fragment?>()

    override fun initPresent() = with(mList)
    {
        Component.state = STATE_NOTIFICATION
        add(NotificationFragment())
        add(MyInformationFragment())
        add(SearcherFragment())
        add(SettingFragment())
        mView.changeUI(this)
        if (!getSharedItem(Component.version, false)) {
            mView.updatedContents()
        }
    }

    override fun checkPattern() = (mList[STATE_INFORMATION] as MyInformationFragment).patternCheck()

    override fun logout() = (mList[STATE_SETTING] as SettingFragment).adminLogout()

    override fun login() = (mList[STATE_SETTING] as SettingFragment).adminLogin()

    override fun resetTimeTable() = (mList[STATE_INFORMATION] as MyInformationFragment).resetTable()

    override fun patternVisibility() {
        (mList[STATE_SETTING] as SettingFragment).patternVisibility()
        (mList[STATE_INFORMATION] as MyInformationFragment).setPatternVisibility()
    }

    override fun changeThemes() = mList.let {
        (it[STATE_NOTIFICATION] as NotificationFragment).themeChanger(true)
        (it[STATE_INFORMATION] as MyInformationFragment).themeChanger(true)
        (it[STATE_SETTING] as SettingFragment).themeChanger()
        (it[STATE_SEARCHER] as SearcherFragment).themeChanger()
    }

    override fun deleteRoomData() = (mList[STATE_SEARCHER] as SearcherFragment).resetDialog()


}