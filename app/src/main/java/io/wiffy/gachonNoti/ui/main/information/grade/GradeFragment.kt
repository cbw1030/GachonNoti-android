package io.wiffy.gachonNoti.ui.main.information.grade

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getThemeButtonResource

class GradeFragment : GradeContract.View() {
    var myView: View? = null
    lateinit var mPresenter: GradePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_grade, container, false)

        mPresenter = GradePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
    }

    fun changeTheme() {
        myView?.findViewById<Button>(R.id.gradeButton)?.setBackgroundResource(getThemeButtonResource())
    }

    fun setViewVisibility(bool: Boolean) {
        val on = myView?.findViewById<RelativeLayout>(R.id.gradeLayoutOn)
        val off = myView?.findViewById<RelativeLayout>(R.id.gradeLayoutOff)
        if (bool) {
            on?.visibility = View.VISIBLE
            off?.visibility = View.GONE
        } else {
            off?.visibility = View.VISIBLE
            on?.visibility = View.GONE
        }
    }


}