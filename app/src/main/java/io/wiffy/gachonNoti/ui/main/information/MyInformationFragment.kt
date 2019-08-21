package io.wiffy.gachonNoti.ui.main.information

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getThemeButtonResource
import io.wiffy.gachonNoti.func.getThemeColor
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.STATE_NOTIFICATION
import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.setting.login.LoginDialog
import kotlinx.android.synthetic.main.fragment_information.view.*


class MyInformationFragment : MyInformationContract.View() {
    lateinit var myView: View
    lateinit var mPresenter: MyInformationPresenter
    lateinit var adapter: PagerAdapter

    companion object {
        lateinit var fragmentList: ArrayList<Fragment?>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information, container, false)
        mPresenter = MyInformationPresenter(this)
        mPresenter.initPresent()
        return myView
    }

    private fun loginExecute() = if (Component.isLogin) {
        isLogin()
    } else {
        isNotLogin()
    }


    override fun isLogin() {
        myView.information_true.visibility = View.VISIBLE
        myView.information_false.visibility = View.GONE
        mPresenter.loginSetting()
    }

    override fun isNotLogin() {
        myView.information_true.visibility = View.GONE
        myView.information_false.visibility = View.VISIBLE
    }

    override fun initView() {
        fragmentList = ArrayList()
        mPresenter.fragmentInflation(fragmentList)
        adapter = PagerAdapter(activity?.supportFragmentManager, fragmentList)
        myView.navigation3.addTab(myView.navigation3.newTab().setText("학생증"))
        myView.navigation3.addTab(myView.navigation3.newTab().setText("시간표"))
        myView.pager3.adapter = adapter
        myView.pager3.offscreenPageLimit = fragmentList.size
        myView.pager3.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(myView.navigation3))
        myView.navigation3.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                myView.pager3.currentItem = tab?.position ?: STATE_NOTIFICATION
            }
        })
        themeChanger(false)

        myView.login2.setOnClickListener {
            LoginDialog(context!!).show()
        }
        loginExecute()
    }

    override fun showLoad() = (activity as MainActivity).builderUp()


    override fun dismissLoad() = (activity as MainActivity).builderDismiss()


    fun themeChanger(bool: Boolean) {
        loginExecute()

        if (bool)
            mPresenter.themeChange()

        myView.login2.setBackgroundResource(getThemeButtonResource())

        intArrayOf(
            Color.parseColor("#8A8989"),
            resources.getColor(getThemeColor())
        ).let {
            myView.navigation3.tabTextColors = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_checked),
                    intArrayOf(android.R.attr.state_checked)
                ), it
            )
            myView.navigation3.setSelectedTabIndicatorColor(it[1])
        }
    }


}