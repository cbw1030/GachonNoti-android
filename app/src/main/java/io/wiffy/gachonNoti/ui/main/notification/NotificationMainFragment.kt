package io.wiffy.gachonNoti.ui.main.notification

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.PagerAdapter
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_notification.*
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
        val themeColorArray = arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        )
        val color = when (Util.theme) {
            "red" -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.red)

            )
            "green" -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.green)

            )
            else -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.main2Blue)

            )
        }
        if (bool)
            mPresenter.themeChange()
        myView.navigation2.tabTextColors = ColorStateList(themeColorArray, color)
        myView.navigation2.setSelectedTabIndicatorColor(color[1])

    }

    override fun initView() {
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


    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }


}