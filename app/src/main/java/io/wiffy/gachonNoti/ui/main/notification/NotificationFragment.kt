package io.wiffy.gachonNoti.ui.main.notification

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.func.getThemeColor
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.model.adapter.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_notification.view.*

import io.wiffy.gachonNoti.ui.main.MainActivity
import android.widget.*
import io.wiffy.gachonNoti.R

class NotificationFragment : NotificationContract.View() {

    lateinit var myView: View
    lateinit var mPresenter: NotificationPresenter
    lateinit var adapter: NotificationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)

        mPresenter = NotificationPresenter(this, context)
        mPresenter.initPresent()

        setRefresh()
        setFab()
        setTypeView()

        return myView
    }

    private fun setRefresh() {
        myView.swipe.setOnRefreshListener {
            mPresenter.resetList()
            myView.swipe.isRefreshing = false
        }
    }

    private fun setFab() {
        myView.fab_main.setOnClickListener {
            val container = FrameLayout(context!!)
            val params =
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = 40
            params.marginEnd = 40
            params.topMargin = 20
            val editText = EditText(activity)
            editText.layoutParams = params
            container.addView(editText)
            editText.hint = "내용"
            AlertDialog.Builder(activity).apply {
                setTitle(
                    "검색어를 입력해주세요."
                )
                setView(container)
                setPositiveButton("검색") { _, _ ->
                    editText.text.toString().apply {
                        if (isNotBlank())
                            mPresenter.search(this)
                    }
                }
                setNegativeButton("취소") { _, _ -> }
            }.create().show()
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
            VerticalSpaceItemDecoration(
                2
            )
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
            myView.swipe.setColorSchemeColors(resources.getColor(getThemeColor()))
            myView.fab_main.backgroundTintList = resources.getColorStateList(getThemeColor())
        }
    }

    override fun updateUI(list: ParseList) = adapter.update(list)

    override fun showLoad() = MainActivity.mView.builderUp()

    override fun dismissLoad() = MainActivity.mView.builderDismiss()

    override fun search(str: String) = mPresenter.search(str)

}


