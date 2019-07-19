package io.wiffy.gachonNoti.ui.main

import android.app.Activity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.information.InformationFragment
import io.wiffy.gachonNoti.ui.main.notification.NotificationMainFragment
import io.wiffy.gachonNoti.ui.main.searcher.SearcherFragment
import io.wiffy.gachonNoti.ui.main.setting.SettingFragment


class MainPresenter(private val mView: MainContract.View, private val context: Activity) : MainContract.Presenter {

    private val mList = ArrayList<Fragment?>()

    override fun initPresent() {
        val notificationFragment = NotificationMainFragment()
        val informationFragment = InformationFragment()
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
        if (!Util.sharedPreferences.getBoolean(context.resources.getString(R.string.whatVersion), false)) {
            mView.updatedContents()
        }
    }

    override fun changeThemes() {
        (mList[Util.STATE_NOTIFICATION] as NotificationMainFragment).themeChanger(true)
        (mList[Util.STATE_INFORMATION]as InformationFragment).themeChanger(true)
        (mList[Util.STATE_SETTING] as SettingFragment).themeChanger()
        (mList[Util.STATE_SEARCHER] as SearcherFragment).themeChanger()
    }

    override fun floatingButtonControl() = (mList[Util.STATE_SEARCHER] as SearcherFragment).floatingButtonControl()
}