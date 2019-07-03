package io.wiffy.gachonNoti.ui.main.searcher


import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.MainContract
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import com.github.eunsiljo.timetablelib.data.TimeTableData
import io.wiffy.gachonNoti.model.Util
import java.util.*
import kotlin.collections.ArrayList

class SearcherFragment : Fragment(), SearchContract.View {
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
        myView.fab.setOnClickListener {
            showLoad()
            builder = SearchDialog(context!!, this, mPresenter)
            builder?.show()
            Handler(Looper.getMainLooper()).post {
                dismissLoad()
            }
        }
        themeChanger()
        setTimeTable(null, "")
    }

    fun themeChanger() {
        myView.fab.backgroundTintList = resources.getColorStateList(
            when (Util.theme) {
                "red" -> R.color.red
                "green" -> R.color.green
                else -> R.color.main2Blue
            }
        )
        myView.semester.setTextColor(
            resources.getColor(
                when (Util.theme) {
                    "red" -> R.color.red
                    "green" -> R.color.green
                    else -> R.color.main2Blue
                }
            )
        )
    }

    override fun setTimeTable(arr: ArrayList<TimeTableData>?, name: String) {
        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = Calendar.getInstance().get(Calendar.MONTH)
        myView.timetable.setStartHour(9)
        myView.timetable.setShowHeader(true)
        myView.timetable.setTableMode(TimeTableView.TableMode.SHORT)

        if (arr == null) {
            myView.showtu.visibility = View.VISIBLE
        } else {
            myView.timetable.setOnTimeItemClickListener { _, _, data ->
                Toast.makeText(activity, data.time.title, Toast.LENGTH_SHORT).show()
            }
            myView.timetable.setTimeTable(0, arr)
            myView.tableName.text = name
            myView.semester.text = when {
                month <= 7 -> "${year}년도 1학기"
                else -> "${year}년도 2학기"
            }
            themeChanger()
            myView.tables.visibility = View.VISIBLE
            myView.showtu.visibility = View.GONE
        }
    }


    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }


}