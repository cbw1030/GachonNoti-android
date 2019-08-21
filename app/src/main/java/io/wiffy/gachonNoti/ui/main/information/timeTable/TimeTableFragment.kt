package io.wiffy.gachonNoti.ui.main.information.timeTable


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.eunsiljo.timetablelib.data.TimeData
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getSharedItem
import com.github.eunsiljo.timetablelib.data.TimeTableData
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.`object`.TimeCompare
import io.wiffy.gachonNoti.func.dayToInt
import io.wiffy.gachonNoti.func.getRandomColorId
import io.wiffy.gachonNoti.func.intToDay
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

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

        Handler(Looper.getMainLooper()).post {

            val superList = ArrayList<ArrayList<TimeData<Any?>?>>(6).apply {
                for (n in 0 until 6) add(ArrayList())
            }
            val realList = ArrayList<ArrayList<TimeData<Any?>>>(6).apply {
                for (n in 0 until 6) add(ArrayList())
            }

            for (value in set.iterator()) {
                value.split("%^").let {
                    superList[dayToInt(it[0])].add(
                        TimeData(
                            0,
                            "${it[1]} - ${it[3].replace("-", "")}",
                            getRandomColorId(),
                            R.color.white,
                            it[4].toLong(),
                            it[5].toLong()
                        )
                    )
                }
                console(value)
            }

            for (v in 0..4) Collections.sort(superList[v], TimeCompare)


            val list = ArrayList<TimeTableData>().apply {
                for (n in 0 until 5) {
                    for (x in 0 until superList[n].size) {
                        if (superList[n][x] == null) continue
                        if (x == superList[n].size - 1) {
                            realList[n].add(
                                TimeData(
                                    1,
                                    superList[n][x]?.title,
                                    getRandomColorId(),
                                    R.color.white,
                                    superList[n][x]?.startMills ?: 0,
                                    superList[n][x]?.stopMills ?: 0
                                )
                            )
                            break
                        }
                        if (superList[n][x]?.title == superList[n][x + 1]?.title) {
                            realList[n].add(
                                TimeData(
                                    0,
                                    superList[n][x]?.title,
                                    getRandomColorId(),
                                    R.color.white,
                                    superList[n][x]?.startMills ?: 0,
                                    superList[n][x + 1]?.stopMills ?: 0
                                )
                            )
                            superList[n][x + 1] = null
                        } else {
                            realList[n].add(
                                TimeData(
                                    0,
                                    superList[n][x]?.title,
                                    getRandomColorId(),
                                    R.color.white,
                                    superList[n][x]?.startMills ?: 0,
                                    superList[n][x]?.stopMills ?: 0
                                )
                            )
                        }
                    }
                    Collections.sort(realList[n], TimeCompare)
                    add(TimeTableData(intToDay(n), realList[n]))
                }
            }
            superList.clear()
            myView?.findViewById<TimeTableView>(R.id.mTable)?.let {
                it.setOnTimeItemClickListener { _, _, data ->
                    toast(data.time.title)
                }
                it.setStartHour(9)
                it.setShowHeader(true)
                it.setTableMode(TimeTableView.TableMode.SHORT)
                it.setTimeTable(0, list)
                console("view")
            }
        }

    }


}