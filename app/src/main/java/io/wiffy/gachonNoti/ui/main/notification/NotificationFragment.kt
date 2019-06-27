package io.wiffy.gachonNoti.ui.main.notification

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.fragment_notification.view.*
import io.wiffy.gachonNoti.ui.main.MainActivity

class NotificationFragment : Fragment(), MainContract.FragmentNotification {

    lateinit var myView: View
    var mPresenter: NotificationPresenter? = null
    lateinit var adapter: MainAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)
        myView.swipe.setOnRefreshListener {
            Util.index=0
            mPresenter?.resetList()
            mPresenter?.initPresent()
            myView.swipe.isRefreshing=false
        }
        if(mPresenter==null) {
            mPresenter = NotificationPresenter(this)
            mPresenter?.initPresent()
        }else{
            changeTheme()
            mPresenter?.uno()
        }
        return myView
    }

    override fun changeUI(list: ParseList) {
        adapter = MainAdapter(list, activity?.applicationContext!!)
        myView.recylcer.adapter = adapter
        myView.recylcer.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer.addItemDecoration(VerticalSpaceItemDecoration(2))
        myView.recylcer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter?.load()
                }
            }
        })
       changeTheme()

    }
fun changeTheme()
{
    myView.swipe.setColorSchemeColors(when(Util.theme){
        "red"->resources.getColor(R.color.red)
        "green"->resources.getColor(R.color.green)
        else->resources.getColor(R.color.main2Blue)
    })
}
    override fun changer(list: ParseList) {
        myView.recylcer.adapter = adapter
        myView.recylcer.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer.addItemDecoration(VerticalSpaceItemDecoration(2))
        myView.recylcer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter?.load()
                }
            }
        })
    }

    override fun updateUI(list: ParseList) {
        adapter.update(list)
    }

    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }

}


class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount!! - 1) {
            outRect.bottom = verticalSpaceHeight
        }
    }
}