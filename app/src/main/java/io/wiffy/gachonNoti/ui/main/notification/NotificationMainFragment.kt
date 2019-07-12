package io.wiffy.gachonNoti.ui.main.notification

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_notification.view.*

class NotificationMainFragment : Fragment(), NotificationMainContract.View {
    lateinit var myView: View
    lateinit var mPresenter: NotificationMainPresenter

    companion object {
        lateinit var fragmentList: ArrayList<Fragment?>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_notification, container, false)
        mPresenter = NotificationMainPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    fun themeChanger() {
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
        mPresenter.themeChange()
        myView.fragmentBottomView.itemTextColor = ColorStateList(themeColorArray, color)

    }

    override fun initView() {
        fragmentList = ArrayList()
        mPresenter.fragmentInflation(fragmentList)
        initInflation()
        themeChanger()

        myView.fragmentBottomView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.notification -> inflateNotification()
                R.id.news -> inflateNews()
                R.id.event -> inflateEvent()
                R.id.scholarship -> inflateScholarship()
            }
            true
        }
    }

    private fun initInflation() {
        for (x in 0..3)
            childFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragmentList[x]!!)
                .commitAllowingStateLoss()

        inflateNotification()
    }

    override fun inflateNotification() {
        childFragmentManager.beginTransaction().hide(fragmentList[1]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[2]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[3]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().show(fragmentList[0]!!)
            .commitAllowingStateLoss()
        mPresenter.themeChange1()
    }

    override fun inflateNews() {
        childFragmentManager.beginTransaction().hide(fragmentList[0]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[2]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[3]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().show(fragmentList[1]!!)
            .commitAllowingStateLoss()
        mPresenter.themeChange2()
    }

    override fun inflateEvent() {
        childFragmentManager.beginTransaction().hide(fragmentList[0]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[1]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[3]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().show(fragmentList[2]!!)
            .commitAllowingStateLoss()
        mPresenter.themeChange3()
    }

    override fun inflateScholarship() {
        childFragmentManager.beginTransaction().hide(fragmentList[0]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[2]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().hide(fragmentList[1]!!)
            .commitAllowingStateLoss()
        childFragmentManager.beginTransaction().show(fragmentList[3]!!)
            .commitAllowingStateLoss()
        mPresenter.themeChange4()
    }

    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }


}