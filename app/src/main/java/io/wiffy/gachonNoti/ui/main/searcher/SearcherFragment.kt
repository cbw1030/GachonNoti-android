package io.wiffy.gachonNoti.ui.main.searcher


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.MainContract
import com.github.eunsiljo.timetablelib.view.TimeTableView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import com.github.eunsiljo.timetablelib.data.TimeTableData
import kotlin.collections.ArrayList

class SearcherFragment : Fragment(), MainContract.FragmentSearcher {
    lateinit var myView: View
    lateinit var mPresenter: SearcherPresenter
    var builder:SearchDialog?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_searcher, container, false)
        mPresenter = SearcherPresenter(this)
        mPresenter.initPresent()
        return myView
    }


    override fun initUI() {
        myView.fab.setOnClickListener {
            builder = SearchDialog(context!!,this,mPresenter)
            builder?.show()
        }
        setTimeTable(null)
    }

    override fun setTimeTable(arr:ArrayList<TimeTableData>?) {
        myView.timetable.setStartHour(9)
        myView.timetable.setShowHeader(true)
        myView.timetable.setTableMode(TimeTableView.TableMode.SHORT)
        if(arr == null){
            val array2 = ArrayList<TimeTableData>()
            array2.add(TimeTableData("월", null))
            array2.add(TimeTableData("화", null))
            array2.add(TimeTableData("수", null))
            array2.add(TimeTableData("목", null))
            array2.add(TimeTableData("금", null))
            myView.timetable.setTimeTable(0, array2)
            myView.showtu.visibility = View.VISIBLE
        }else{
            myView.timetable.setTimeTable(0, arr)
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