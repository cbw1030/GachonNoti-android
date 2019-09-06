package io.wiffy.gachonNoti.ui.main.notification

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.function.getThemeColor
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.`object`.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_notification.view.*
import io.wiffy.gachonNoti.ui.main.MainActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.getDarkColor1
import io.wiffy.gachonNoti.function.getDarkColor2
import io.wiffy.gachonNoti.model.adapter.NotificationAdapter

@Suppress("DEPRECATION")
class NotificationFragment : NotificationContract.View() {

    private lateinit var myView: View
    lateinit var mPresenter: NotificationPresenter
    lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)

        mPresenter = NotificationPresenter(this)
        mPresenter.initPresent()

        setRefresh()
        setFab()
        setTypeView()

        return myView
    }

    private fun setRefresh() {
        myView.swipe.setOnRefreshListener {
            adapter.notifyDataSetChanged()
            mPresenter.resetList()
            myView.swipe.isRefreshing = false
        }
    }

    override fun recyclerViewClear() = myView.recylcer.recycledViewPool.clear()

    private fun setFab() {
        myView.fab_main.setOnClickListener {
            MaterialDialog(activity!!).show {
                title(text = "검색어를 입력해주세요.")
                input(hint = "내용") { _, text ->
                    text.toString().apply {
                        if (isNotBlank())
                            mPresenter.search(this)
                    }
                }
                positiveButton(text = "검색")
                negativeButton(text = "취소")
            }
        }
    }

    private fun setTypeView() {
        myView.segmented.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.button1 -> mPresenter.setType(0)
                R.id.button2 -> mPresenter.setType(1)
                R.id.button3 -> mPresenter.setType(2)
                R.id.button4 -> mPresenter.setType(3)
            }
        }
    }

    override fun internetUnusable() = Handler(Looper.getMainLooper()).post {
        myView.par1.visibility = View.GONE
        myView.lottieXX.visibility = View.VISIBLE
    }

    override fun internetUsable() = Handler(Looper.getMainLooper()).post {
        myView.par1.visibility = View.VISIBLE
        myView.lottieXX.visibility = View.GONE
    }

    override fun changeUI(list: ParseList) {
        myView.button1.isChecked = true
        adapter = NotificationAdapter(
            list,
            activity?.applicationContext!!,
            activity as MainActivity,
            0
        )
        myView.recylcer.adapter = adapter
        myView.recylcer.layoutManager = LinearLayoutManager(activity?.applicationContext!!)
        myView.recylcer.addItemDecoration(
            VerticalSpaceItemDecoration
        )
        myView.recylcer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    mPresenter.pageUp()
                }
            }
        })
        themeChanger(true)
    }

    override fun themeChanger(bool: Boolean) {
        if (bool) {
            if (Component.darkTheme) {
                myView.netnet.setTextColor(resources.getColor(R.color.white))
                myView.notification_card.setCardBackgroundColor(resources.getColor(getDarkColor1()))
                myView.backbackback.setBackgroundColor(resources.getColor(R.color.myDarkDeep))
                myView.swipe.setColorSchemeColors(resources.getColor(getDarkColor1()))
                myView.fab_main.backgroundTintList =
                    resources.getColorStateList(R.color.myDarkLight)
                myView.segmented.setTintColor(
                    resources.getColor(R.color.white),
                    resources.getColor(R.color.myDarkDeep)
                )
            } else {
                myView.swipe.setColorSchemeColors(resources.getColor(getThemeColor()))
                myView.fab_main.backgroundTintList = resources.getColorStateList(getThemeColor())
                myView.segmented.setTintColor(resources.getColor(getThemeColor()))
            }
        }
    }

    override fun updateUI(list: ParseList) = adapter.update(list)

    override fun search(str: String) = mPresenter.search(str)

    override fun sendContext() = context

}


