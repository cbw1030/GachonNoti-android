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
        with(list) {
            notifications = NotificationFragment()
            news = NewsFragment()
            event = EventFragment()
            scholarship = ScholarshipFragment()
            add(notifications)
            add(news)
            add(event)
            add(scholarship)
        }

    }

    override fun search(state: Int, str: String) {
        when (state) {
            0 -> if (notifications != null) notifications?.search(str)
            1 -> if (news != null) news?.search(str)
            2 -> if (event != null) event?.search(str)
            3 -> if (scholarship != null) scholarship?.search(str)
        }
    }

    override fun themeChange() {
        if (notifications != null) notifications?.changeTheme()
        if (news != null) news?.changeTheme()
        if (event != null) event?.changeTheme()
        if (scholarship != null) scholarship?.changeTheme()
    }

}