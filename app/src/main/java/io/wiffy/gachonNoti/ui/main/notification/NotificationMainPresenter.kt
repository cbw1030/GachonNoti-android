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

    private var notifications: NotificationFragment? = null
    private var news: NewsFragment? = null
    private var event: EventFragment? = null
    private var scholarship: ScholarshipFragment? = null

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
            0 -> notifications?.search(str)
            1 -> news?.search(str)
            2 -> event?.search(str)
            3 -> scholarship?.search(str)
        }
    }

    override fun themeChange() {
        notifications?.changeTheme()
        news?.changeTheme()
        event?.changeTheme()
        scholarship?.changeTheme()
    }

}