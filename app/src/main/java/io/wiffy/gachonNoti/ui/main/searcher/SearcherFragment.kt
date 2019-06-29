package io.wiffy.gachonNoti.ui.main.searcher

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.MainContract
import java.util.*
import android.widget.ArrayAdapter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_searcher.view.*


class SearcherFragment : Fragment(), MainContract.FragmentSearcher {
    lateinit var myView: View
    lateinit var mPresenter: SearcherPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_searcher, container, false)
        mPresenter = SearcherPresenter(this)
        mPresenter.initPresent()
        return myView
    }


    override fun initUI() {
        val semester:String

        val year = Calendar.getInstance().get(Calendar.YEAR).toString()
        myView.year.setText(year)

        val month = Calendar.getInstance().get(Calendar.MONTH)
        myView.semester.setText(when{
            month<=6 -> {
                semester="1"
                "1학기"
            }
            else -> {
                semester="2"
                "2학기"
            }
        })

        val yearSemester = "$year-$semester"

        myView.checkTime.setOnClickListener {
            myView.getdate.visibility =
                when (myView.checkTime.isChecked) {
                    true -> {
                        View.VISIBLE
                    }
                    false -> {
                        View.GONE
                    }
                }
        }

        myView.getdata.setOnClickListener {
            getDataDialog(yearSemester)
        }

        mPresenter.isDownloaded(year, semester)
    }

    override fun showBtn(c: Boolean) {
        if (c) {
            myView.getdata.visibility = View.VISIBLE
            myView.cate.visibility = View.GONE
        } else {
            myView.getdata.visibility = View.GONE
            myView.cate.visibility = View.VISIBLE
        }
    }


    override fun getDataDialog(yearSemester:String) {
        val builder = AlertDialog.Builder(activity, R.style.light_dialog)
        builder.setTitle("시간표 데이터를 가져옵니다.")
        builder.setMessage("시간이 다소 걸릴 수 있으니 중간에 앱을 종료하지 마세요.")
        builder.setPositiveButton(
            "OK"
        ) { _, _ -> mPresenter.getData(yearSemester) }
        builder.show()
    }

    override fun errorDialog() {
        val builder = AlertDialog.Builder(activity, R.style.light_dialog)
        builder.setTitle("오류")
        builder.setMessage("시간표 데이터가 없습니다.")
        builder.setPositiveButton(
            "OK"
        ) { _, _ -> }
        builder.show()
    }

    override fun showLoad() {
        (activity as MainActivity).builderUp()
    }

    override fun dismissLoad() {
        (activity as MainActivity).builderDismiss()
    }

    override fun setSpinner(arrayList:ArrayList<String>){
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayList)
        myView.cate.adapter = arrayAdapter
    }
}