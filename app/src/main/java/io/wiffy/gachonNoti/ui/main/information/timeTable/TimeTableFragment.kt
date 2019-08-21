package io.wiffy.gachonNoti.ui.main.information.timeTable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getSharedItem

class TimeTableFragment : TimeTableContract.View() {
    var myView: View? = null
    lateinit var mPresenter: TimeTablePresenter
    private var mInfo: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_timetable, container, false)

        mPresenter = TimeTablePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun initView() {
        changeTheme()
        mInfo?.let {
            loginInformationSetting(it)
        }
    }

    fun changeTheme() {

    }

    fun loginInformationSetting(info: String) {
        mInfo = info
        myView?.let {
            if (info.length > 6) {
                val mTable = getSharedItem<HashSet<String>>("tableSet")
                if (mTable.size <= 0) {
                    TimeTableAsyncTask(this@TimeTableFragment, info).execute()
                } else {
                    initTable(mTable)
                }
            }
        }
    }

    override fun initTable(set: HashSet<String>) {
    
    }
}