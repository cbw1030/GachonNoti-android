package io.wiffy.gachonNoti.ui.main.notification.event

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.Util.Companion.getThemeColor
import io.wiffy.gachonNoti.model.VerticalSpaceItemDecoration
import io.wiffy.gachonNoti.model.adapter.NotificationComponentAdapter
import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract
import kotlinx.android.synthetic.main.fragment_notification_event.view.*

class EventFragment : NotificationComponentContract.View() {
    lateinit var myView: View
    lateinit var mPresenter: EventPresenter
    lateinit var adapter: NotificationComponentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification_event, container, false)
        mPresenter = EventPresenter(this, context)
        myView.swipe3?.setOnRefreshListener {
            mPresenter.resetList()
            myView.swipe3?.isRefreshing = false
        }
        mPresenter.initPresent()
        return myView
    }

    override fun changeUI(list: ParseList) {
        adapter = NotificationComponentAdapter(
            list,
            activity?.applicationContext!!,
            activity as MainActivity,
            3
        )
        myView.recylcer3.adapter = adapter
        myView.recylcer3.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer3.addItemDecoration(
            VerticalSpaceItemDecoration(
                2
            )
        )
        myView.recylcer3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter.load()
                }
            }
        })

        changeTheme()
    }

    override fun internetUnusable() = Handler(Looper.getMainLooper()).post {
        myView.par3.visibility = View.GONE
        myView.lottieXX3.visibility = View.VISIBLE
    }


    override fun internetUsable() = Handler(Looper.getMainLooper()).post {
        myView.par3.visibility = View.VISIBLE
        myView.lottieXX3.visibility = View.GONE
    }

    fun changeTheme() = myView.swipe3.setColorSchemeColors(resources.getColor(getThemeColor()))

    override fun updateUI(list: ParseList) = adapter.update(list)

    override fun showLoad() = MainActivity.mView.builderUp()

    override fun dismissLoad() = MainActivity.mView.builderDismiss()


    fun search(str: String) = mPresenter.search(str)

}