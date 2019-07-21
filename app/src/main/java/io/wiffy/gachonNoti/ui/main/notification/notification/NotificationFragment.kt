package io.wiffy.gachonNoti.ui.main.notification.notification

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.data.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_notification_notification.view.*

import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.notification.NotificationComponentContract

class NotificationFragment : Fragment(),
    NotificationComponentContract.View {

    lateinit var myView: View
    lateinit var mPresenter: NotificationPresenter
    lateinit var adapter: NotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification_notification, container, false)

        mPresenter = NotificationPresenter(this, context)
        myView.swipe.setOnRefreshListener {
            mPresenter.resetList()
            myView.swipe.isRefreshing = false
        }
        mPresenter.initPresent()

        return myView
    }

    override fun internetUnusable() {
        Handler(Looper.getMainLooper()).post {
            myView.par1.visibility = View.GONE
            myView.lottieXX.visibility = View.VISIBLE
        }
    }

    override fun internetUsable() {
        Handler(Looper.getMainLooper()).post {
            myView.par1.visibility = View.VISIBLE
            myView.lottieXX.visibility = View.GONE
        }
    }

    override fun changeUI(list: ParseList) {
        adapter = NotificationAdapter(
            list,
            activity?.applicationContext!!,
            activity as MainActivity
        )
        myView.recylcer.adapter = adapter
        myView.recylcer.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer.addItemDecoration(
            VerticalSpaceItemDecoration(
                2
            )
        )
        myView.recylcer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter.load()
                }
            }
        })
        changeTheme()

    }

    fun changeTheme() {

        myView.swipe.setColorSchemeColors(
            when (Util.theme) {
                "red" -> resources.getColor(R.color.red)
                "green" -> resources.getColor(R.color.green)
                else -> resources.getColor(R.color.main2Blue)
            }
        )
    }

    override fun updateUI(list: ParseList) {
        adapter.update(list)
    }

    override fun showLoad() {
        MainActivity.mView.builderUp()
    }

    override fun dismissLoad() {
        MainActivity.mView.builderDismiss()
    }
    fun search(str:String)
    {
        mPresenter.search(str)
    }
}


