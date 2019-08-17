package io.wiffy.gachonNoti.ui.main.notification

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.Util.Companion.getThemeColor
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_notification.view.*
import kotlinx.android.synthetic.main.fragment_notification.view.navigation2

class NotificationMainFragment : Fragment(), NotificationMainContract.View {
    lateinit var myView: View
    lateinit var mPresenter: NotificationMainPresenter
    lateinit var adapter: PagerAdapter

    companion object {
        lateinit var fragmentList: ArrayList<Fragment?>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)
        mPresenter = NotificationMainPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    fun themeChanger(bool: Boolean) {
        myView.fab_main.backgroundTintList = resources.getColorStateList(getThemeColor())
        if (bool)
            mPresenter.themeChange()
        intArrayOf(
            Color.parseColor("#8A8989"),
            resources.getColor(getThemeColor())

        ).let {
            myView.navigation2.tabTextColors = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked)
                ), it
            )
            myView.navigation2.setSelectedTabIndicatorColor(it[1])
        }
    }

    override fun initView() {
        Glide.with(context!!).load(R.drawable.search).into(myView.fab_main)
        myView.fab_main.setOnClickListener {
            val container = FrameLayout(context!!)
            val params =
                FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginStart = 40
            params.marginEnd = 40
            val editText = EditText(activity)
            editText.layoutParams = params
            container.addView(editText)
            editText.hint = "내용"
            AlertDialog.Builder(activity).apply {
                setTitle(
                    "검색 [${when (Util.checkStateInNotification) {
                        0 -> "공지사항"
                        1 -> "가천뉴스"
                        2 -> "행사소식"
                        3 -> "장학소식"
                        else -> ""
                    }}]"
                )
                setView(container)
                setMessage("\n검색어를 입력해주세요.")
                setPositiveButton("검색") { _, _ ->
                    editText.text.toString().apply {
                        if (isNotBlank())
                            mPresenter.search(Util.checkStateInNotification, this)
                    }
                }
                setNegativeButton("취소") { _, _ -> }
            }.create().show()
        }
        fragmentList = ArrayList()
        mPresenter.fragmentInflation(fragmentList)
        adapter = PagerAdapter(activity?.supportFragmentManager, fragmentList)
        myView.navigation2.addTab(myView.navigation2.newTab().setText("공지사항"))
        myView.navigation2.addTab(myView.navigation2.newTab().setText("가천뉴스"))
        myView.navigation2.addTab(myView.navigation2.newTab().setText("행사소식"))
        myView.navigation2.addTab(myView.navigation2.newTab().setText("장학소식"))
        myView.pager2.adapter = adapter
        myView.pager2.offscreenPageLimit = fragmentList.size
        myView.pager2.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(myView.navigation2))
        myView.navigation2.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                myView.pager2.currentItem = tab?.position ?: Util.STATE_NOTIFICATION
                Util.checkStateInNotification = tab?.position ?: Util.STATE_NOTIFICATION
                MainActivity.mView.setTabText(
                    when (tab?.position) {
                        1 -> "가천뉴스"
                        2 -> "행사소식"
                        3 -> "장학소식"
                        else -> "공지사항"
                    }
                )
            }
        })
        themeChanger(false)
    }


    override fun showLoad() = (activity as MainActivity).builderUp()


    override fun dismissLoad() = (activity as MainActivity).builderDismiss()

}