package io.wiffy.gachonNoti.ui.main.notification.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R

class NewsFragment : Fragment() {
    lateinit var myView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification_news, container, false)

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}