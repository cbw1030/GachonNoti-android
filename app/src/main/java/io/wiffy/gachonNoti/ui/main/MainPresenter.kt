package io.wiffy.gachonNoti.ui.main

import android.app.Activity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.information.MyInformationFragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationMainFragment
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View, private val context: Activity) : MainContract.Presenter {

    private val mList = ArrayList<Fragment?>()

    override fun initPresent() {
        val notificationFragment = NotificationMainFragment()
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
        if (!Util.sharedPreferences.getBoolean(Util.version, false)) {
            mView.updatedContents()
        }
    }

    override fun changeThemes() {
        (mList[Util.STATE_NOTIFICATION] as NotificationMainFragment).themeChanger(true)
        (mList[Util.STATE_INFORMATION]as MyInformationFragment).themeChanger(true)
        (mList[Util.STATE_SETTING] as SettingFragment).themeChanger()
        (mList[Util.STATE_SEARCHER] as SearcherFragment).themeChanger()
    }

    override fun resetData() {
        (mList[Util.STATE_SEARCHER] as SearcherFragment).resetDialog()
    }

}