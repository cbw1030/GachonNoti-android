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
import io.wiffy.gachonNoti.model.PagerAdapter
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainActivity
import io.wiffy.gachonNoti.ui.main.setting.DetailDialog
import kotlinx.android.synthetic.main.fragment_information.view.*
import kotlinx.android.synthetic.main.fragment_notification.view.*


class InformationFragment : Fragment(), InformationContract.View {
    lateinit var myView: View
    lateinit var mPresenter: InformationPresenter
    lateinit var adapter: PagerAdapter

    companion object {
        lateinit var fragmentList: ArrayList<Fragment?>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information, container, false)
        mPresenter = InformationPresenter(this)
        loginExecute()
        mPresenter.initPresent()
        return myView
    }

    private fun loginExecute() {
        if (Util.isLogin) {
            isLogin()
        } else {
            isNotLogin()
        }
    }

    override fun isLogin() {
        myView.information_true.visibility = View.VISIBLE
        myView.information_false.visibility = View.GONE
    }

    override fun isNotLogin() {
        myView.information_true.visibility = View.GONE
        myView.information_false.visibility = View.VISIBLE
    }

    override fun initView() {
        fragmentList = ArrayList()
        mPresenter.fragmentInflation(fragmentList)
        adapter = PagerAdapter(activity?.supportFragmentManager, fragmentList)
        myView.navigation3.addTab(myView.navigation3.newTab().setText("내정보"))
        myView.pager3.adapter = adapter
        myView.pager3.offscreenPageLimit = fragmentList.size
        myView.pager3.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(myView.navigation3))
        myView.navigation3.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                myView.pager2.currentItem = tab?.position ?: Util.STATE_NOTIFICATION
            }
        })
        themeChanger(false)

        myView.login2.setOnClickListener {


            val builder = DetailDialog(context!!)
            builder.show()
            builder.setListener(
                View.OnClickListener {
                    builder.okListen()
                    (activity as MainActivity).themeChange()
                    (activity as MainActivity).mPresenter.changeThemes()
                    builder.dismiss()
                },
                View.OnClickListener {
                    (activity as MainActivity).mPresenter.changeThemes()
                    builder.dismiss()
                })
        }


    }

    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }

    fun themeChanger(bool: Boolean) {
        loginExecute()
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
        myView.navigation3.tabTextColors = ColorStateList(themeColorArray, color)
        myView.navigation3.setSelectedTabIndicatorColor(color[1])

    }
}