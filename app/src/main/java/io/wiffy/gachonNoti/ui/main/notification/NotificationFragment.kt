package io.wiffy.gachonNoti.ui.main.notification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainContract

class NotificationFragment: Fragment(),MainContract.FragmentNotification {
    lateinit var myView:View
    lateinit var mPresenter: NotificationPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       myView = inflater.inflate(R.layout.fragment_notification,container,false)
        mPresenter = NotificationPresenter(this)
        return myView
    }
}