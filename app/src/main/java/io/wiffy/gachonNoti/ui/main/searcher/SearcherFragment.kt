package io.wiffy.gachonNoti.ui.main.searcher


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.Component
import kotlin.collections.ArrayList

class SearcherFragment : SearchContract.View() {
    lateinit var myView: View
    lateinit var mPresenter: SearcherPresenter

    var builder: SearchDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_searcher, container, false)
        mPresenter = SearcherPresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun initUI() {
        Glide.with(this).load(R.drawable.search).into(myView.fab)

        myView.fab.setOnClickListener {
            builder = SearchDialog(context!!, this, mPresenter)
            builder?.show()
        }

        themeChanger()
        setTimeTable(null, "")
    }

    @SuppressLint("ApplySharedPref")
    fun resetDialog(): AlertDialog = AlertDialog.Builder(context).apply {
        setMessage("저장된 데이터를 재설정 할까요?")
        setPositiveButton(
            "OK"
        ) { _, _ ->
            for (V in Component.timeTableSet.iterator()) {
                removeSharedItem(V)
            }
            Component.timeTableSet.clear()
            setSharedItem("tableItems", Component.timeTableSet)

            this@SearcherFragment.isVisible = true

            builder = SearchDialog(context!!, this@SearcherFragment, mPresenter)
            builder?.show()
        }
    }.show()


    fun themeChanger() {
        myView.fab.backgroundTintList = resources.getColorStateList(getThemeColor())
        myView.semester.setTextColor(resources.getColor(getThemeColor()))
    }

    @SuppressLint("SetTextI18n")
    override fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String) {
        myView.timetable.setStartHour(9)
        myView.timetable.setShowHeader(true)
        myView.timetable.setTableMode(TimeTableView.TableMode.SHORT)

        arr.ifNotNullOrElse({
            myView.timetable.setOnTimeItemClickListener { _, _, data ->
                toast(data.time.title)
            }
            myView.timetable.setTimeTable(0, arr)

            MainActivity.mView.title = "(${when (Component.SEMESTER) {
                1 -> "1"
                2 -> "2"
                3 -> "여름"
                else -> "겨울"
            }}학기) $name "
            themeChanger()
            this@SearcherFragment.isVisible = false

        },
            { this@SearcherFragment.isVisible = true })


    }

    override fun setVisible(bool: Boolean) = if (bool) {
        myView.showtu.visibility = View.VISIBLE
        myView.timetable.visibility = View.GONE
        MainActivity.mView.title = "강의실"
    } else {
        myView.showtu.visibility = View.GONE
        myView.timetable.visibility = View.VISIBLE
    }

    override fun showLoad() = (activity as MainActivity).builderUp()


    override fun dismissLoad() = (activity as MainActivity).builderDismiss()

}