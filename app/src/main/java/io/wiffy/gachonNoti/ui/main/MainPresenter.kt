package io.wiffy.gachonNoti.ui.main

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View) : MainContract.Presenter {

    private val mList = ArrayList<Fragment>()

    override fun initPresent() {
        val notificationFragment = NotificationFragment()
        val searcherFragment = SearcherFragment()
        val settingFragment = SettingFragment()
        mList.add(notificationFragment)
        mList.add(searcherFragment)
        mList.add(settingFragment)
        mView.changeUI(mList)
    }

    override fun changeThemes() {
        (mList[Util.STATE_SETTING] as SettingFragment).themeChanger()
        (mList[Util.STATE_SEARCHER] as SearcherFragment).themeChanger()
    }
}