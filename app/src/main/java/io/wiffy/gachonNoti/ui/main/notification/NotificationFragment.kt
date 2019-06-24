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
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.fragment_notification.view.*

class NotificationFragment : Fragment(), MainContract.FragmentNotification {
    lateinit var myView: View
    lateinit var mPresenter: NotificationPresenter
    lateinit var adapter: MainAdapter
    lateinit var list: ParseList
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)
        list = (arguments?.getSerializable("myList") as ParseList)
        mPresenter = NotificationPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun changeUI() {
        adapter = MainAdapter(list,activity?.applicationContext!!)
        myView.recylcer.adapter = adapter
        myView.recylcer.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer.addItemDecoration(VerticalSpaceItemDecoration(2))
    }

}


class VerticalSpaceItemDecoration(private val verticalSpaceHeight:Int):RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if(parent.getChildAdapterPosition(view)!=parent.adapter?.itemCount!!-1){
            outRect.bottom=verticalSpaceHeight
        }
    }
}