package io.wiffy.gachonNoti.ui.main.information.timeTable

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wiffy.gachonNoti.R
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.model.`object`.Component
import io.wiffy.gachonNoti.utils.doneLogin
import io.wiffy.gachonNoti.utils.getDarkColor1
import io.wiffy.gachonNoti.utils.getThemeColor
import kotlinx.android.synthetic.main.fragment_information_timetable.view.*
import kotlin.collections.HashSet

@Suppress("DEPRECATION")
class TimeTableFragment : TimeTableContract.View() {
    var myView: View? = null
    lateinit var mPresenter: TimeTablePresenter
    private var mInfo: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_information_timetable, container, false)

        mPresenter = TimeTablePresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun sendContext() = context
    override fun initView() {
        changeTheme()
        mInfo?.let {
            loginInformationSetting(it)
        } ?: doneLogin(requireActivity(), context!!)
        myView?.swipe?.setOnRefreshListener {
            mPresenter.resetTable()
            myView?.swipe?.isRefreshing = false
        }
    }

    fun changeTheme() {
        myView?.swipe?.setColorSchemeColors(
            if (Component.darkTheme) {
                resources.getColor(getDarkColor1())
            } else {
                resources.getColor(getThemeColor())
            }
        )
    }

    fun loginInformationSetting(info: String) {
        mInfo = info
        myView?.let {
            if (info.length > 6) {
                mPresenter.setLogin(info)
            }
        }
    }

    fun resetTable() = mPresenter.resetTable()

    override fun initTable(set: HashSet<String>) {
        Handler(Looper.getMainLooper()).post {
            val list = mPresenter.setTableList(set)
            myView?.mTable?.let {
                it.setOnTimeItemClickListener { _, _, data ->
                    toast(data.time.title)
                }
                it.setStartHour(9)
                it.setShowHeader(true)
                it.setTableMode(TimeTableView.TableMode.SHORT)
                it.setTimeTable(0, list)
            }
        }

    }
}