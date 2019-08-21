package io.wiffy.gachonNoti.ui.main.information.timeTable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wiffy.gachonNoti.R

class TimeTableFragment() : TimeTableContract.View() {
    var myView: View? = null
    lateinit var mPresenter: TimeTablePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_timetable, container, false)

        mPresenter = TimeTablePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {

    }

    fun changeTheme() {


    }

    fun loginInformationSetting() {

    }
}