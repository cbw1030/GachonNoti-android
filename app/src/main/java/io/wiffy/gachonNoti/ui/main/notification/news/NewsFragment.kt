package io.wiffy.gachonNoti.ui.main.notification.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.MainAdapter
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_notification_news.view.*

class NewsFragment : Fragment(), NewsContract.View {
    lateinit var myView: View
    lateinit var mPresenter: NewsPresenter
    lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification_news, container, false)

        mPresenter = NewsPresenter(this, context)
        myView.swipe2.setOnRefreshListener {
            mPresenter.resetList()
            myView.swipe2.isRefreshing = false
        }
        mPresenter.initPresent()
        return myView
    }

    override fun changeUI(list: ParseList) {


        changeTheme()
    }

    override fun internetUnusable() {

    }

    override fun internetUsable() {

    }

    override fun updateUI(list: ParseList) {

    }

    fun changeTheme() {
        myView.swipe2.setColorSchemeColors(
            when (Util.theme) {
                "red" -> resources.getColor(R.color.red)
                "green" -> resources.getColor(R.color.green)
                else -> resources.getColor(R.color.main2Blue)
            }
        )
    }

    override fun showLoad() {
        MainActivity.mView.builderUp()
    }

    override fun dismissLoad() {
        MainActivity.mView.builderDismiss()
    }
}