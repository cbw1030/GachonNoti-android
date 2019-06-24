package io.wiffy.gachonNoti.ui.main.notification

import io.wiffy.gachonNoti.ui.main.MainContract

class NotificationPresenter(private val mView:MainContract.FragmentNotification):MainContract.PresenterNotification {
    override fun initPresent() {
        mView.changeUI()
    }
}