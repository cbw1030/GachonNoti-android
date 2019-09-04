package io.wiffy.gachonNoti.ui.main.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.getThemeButtonResource
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.getDarkColor1
import io.wiffy.gachonNoti.function.getThemeColor
import io.wiffy.gachonNoti.ui.main.setting.login.LoginDialog
import kotlinx.android.synthetic.main.fragment_information.view.*
import kotlin.system.exitProcess


class MyInformationFragment : MyInformationContract.View() {
    lateinit var myView: View
    var mPresenter: MyInformationPresenter? = null
    lateinit var adapter: PagerAdapter

    companion object {
        lateinit var fragmentList: ArrayList<Fragment?>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_information, container, false)
        mPresenter = MyInformationPresenter(this)
        mPresenter?.initPresent()
        return myView
    }

    fun loginExecute() = if (Component.isLogin) {
        isLogin()
    } else {
        isNotLogin()
    }

    fun resetTable() = mPresenter?.resetTable()

    override fun isLogin() {
        try {
            myView.information_true.visibility = View.VISIBLE
            myView.information_false.visibility = View.GONE
            mPresenter?.loginSetting()
        } catch (e: Exception) {
            exitProcess(0)
        }
    }

    override fun isNotLogin() {
        myView.information_true.visibility = View.GONE
        myView.information_false.visibility = View.VISIBLE
    }

    override fun initView() {
        myView.button1_.isChecked = true
        fragmentList = ArrayList()
        mPresenter?.fragmentInflation(fragmentList)
        adapter = PagerAdapter(activity?.supportFragmentManager, fragmentList)
        myView.pager3.setPagingEnabled(false)
        myView.pager3.adapter = adapter
        myView.pager3.offscreenPageLimit = fragmentList.size
        themeChanger(false)

        myView.login2.setOnClickListener {
            LoginDialog(context!!).show()
        }
        loginExecute()
        setTypeView()
    }

    private fun setTypeView() {
        myView.segmented2.setOnCheckedChangeListener { _, checkedId ->
            myView.pager3.currentItem =
                when (checkedId) {
                    R.id.button1_ -> 0
                    R.id.button2_ -> 1
                    R.id.button3_ -> 2
                    R.id.button4_ -> 3
                    else -> 0
                }
        }
    }

    override fun setPatternVisibility() = mPresenter?.setPatternVisibility()

    fun themeChanger(bool: Boolean) {

        if (bool) mPresenter?.themeChange()

        if (Component.darkTheme) {
            myView.segmented2.setTintColor(
                resources.getColor(R.color.white),
                resources.getColor(R.color.myDarkDeep)
            )
            myView.information_true.setBackgroundColor(resources.getColor(R.color.myDarkDeep))
            myView.information_false.setBackgroundColor(resources.getColor(R.color.myDarkDeep))
            myView.loghara.setCardBackgroundColor(resources.getColor(getDarkColor1()))
            myView.text.setTextColor(resources.getColor(R.color.white))
            myView.text2.setTextColor(resources.getColor(R.color.white))
        } else
            myView.segmented2.setTintColor(resources.getColor(getThemeColor()))
        myView.login2.setBackgroundResource(getThemeButtonResource())

    }
}