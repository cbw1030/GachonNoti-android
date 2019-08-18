package io.wiffy.gachonNoti.ui.main

import android.app.Activity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.information.MyInformationFragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragmentM
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View, private val context: Activity) : MainContract.Presenter {

    private val mList = ArrayList<Fragment?>()

    override fun initPresent() {
        Util.state = STATE_NOTIFICATION

        val notificationFragment = NotificationFragmentM()
        val informationFragment = MyInformationFragment()
        val searcherFragment = SearcherFragment()
        val settingFragment = SettingFragment()

        with(mList)
        {
            add(notificationFragment)
            add(searcherFragment)
            add(informationFragment)
            add(settingFragment)
        }

        mView.changeUI(mList)
        if (!getSharedItem(Util.version, false)) {
            mView.updatedContents()
        }
    }

    override fun changeThemes() = mList.let {
        //(it[STATE_NOTIFICATION] as NotificationFragmentM).themeChanger(true)
        (it[STATE_INFORMATION] as MyInformationFragment).themeChanger(true)
        (it[STATE_SETTING] as SettingFragment).themeChanger()
        (it[STATE_SEARCHER] as SearcherFragment).themeChanger()
    }

    override fun resetData() = (mList[STATE_SEARCHER] as SearcherFragment).resetDialog()

}