package io.wiffy.gachonNoti.ui.main.notification

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.notification.event.EventFragment
import io.wiffy.gachonNoti.ui.main.notification.news.NewsFragment
import io.wiffy.gachonNoti.ui.main.notification.notification.NotificationFragment
import io.wiffy.gachonNoti.ui.main.notification.scholarship.ScholarshipFragment


class NotificationMainPresenter(private val mView: NotificationMainContract.View) : NotificationMainContract.Presenter {


    override fun initPresent() {
        mView.initView()
    }

    var notifications: NotificationFragment? = null
    var news: NewsFragment? = null
    var event: EventFragment? = null
    var scholarship: ScholarshipFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) {
        notifications = NotificationFragment()
        news = NewsFragment()
        event = EventFragment()
        scholarship = ScholarshipFragment()
        list.add(notifications)
        list.add(news)
        list.add(event)
        list.add(scholarship)
    }

    override fun themeChange() {
        if (notifications != null) notifications?.changeTheme()
        if (news != null) news?.changeTheme()
        if (event != null) event?.changeTheme()
        if (scholarship != null) scholarship?.changeTheme()
    }

}