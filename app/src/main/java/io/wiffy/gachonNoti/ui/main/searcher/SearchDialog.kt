package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_search.*
import java.util.*
import kotlin.collections.ArrayList


class SearchDialog(
    context: Context,
    private val mView: SearchContract.View,
    private val mPresenter: SearcherPresenter
) : Dialog(context), SearchContract.DialogPresenter {

    var spinnerSelected = 0
    lateinit var yearr: String
    lateinit var semester: String


    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search)

        mPresenter.initPresentDialog(this)

        yearr = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = Calendar.getInstance().get(Calendar.MONTH)
        year.text = when (month) {
            in 2..5 -> {
                semester = "1"
                "${yearr}년도 1학기"
            }
            in 6..7 -> {
                semester = "3"
                "${yearr}년도 여름학기"
            }

            in 8..11 -> {
                semester = "2"
                "${yearr}년도 2학기"
            }
            else -> {
                semester = "4"
                "${yearr}년도 겨울학기"
            }
        }


        getdata.setOnClickListener {
            getDataDialog("$yearr-$semester")
        }

        search.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                mView.showLoad()
            }
            mPresenter.loadRoom(cate.getItemAtPosition(spinnerSelected).toString())
        }

        cate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerSelected = position
            }
        }

        mPresenter.isDownloaded(yearr, semester)
    }

    override fun cate_invi() {
        cate.visibility = View.INVISIBLE
    }

    override fun dismissSelf() {
        dismiss()
    }

    override fun getDataDialog(yearSemester: String) {
        if (Util.isNetworkConnected(context)) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("시간표 데이터를 가져옵니다.")
            builder.setMessage("시간이 다소 걸릴 수 있으니 중간에 앱을 종료하지 마세요.\n(최초 한번만 다운로드 합니다.)")
            builder.setPositiveButton("OK") { _, _ ->
                mPresenter.getData(yearSemester)
            }
            builder.show()
        } else {
            Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun errorDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("오류")
        builder.setMessage("시간표 데이터가 없습니다.")
        builder.setPositiveButton(
            "OK"
        ) { _, _ -> }
        builder.show()
    }

    override fun showBtn(c: Boolean) {
        if (c) {
            getdata.visibility = View.VISIBLE
            cate.visibility = View.GONE
            search.visibility = View.GONE
        } else {
            getdata.visibility = View.GONE
            cate.visibility = View.VISIBLE
            search.visibility = View.VISIBLE
        }
    }

    override fun requestLoad() {
        mPresenter.isDownloaded(yearr, semester)
    }

    override fun setSpinner(arrayList: ArrayList<String>) {
        cate.visibility = View.VISIBLE
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayList)
        cate.adapter = arrayAdapter
    }

    override fun setListDialog(arrayList: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1)
        val lp = WindowManager.LayoutParams()
        for (x in arrayList) {
            adapter.add(x)
        }
        builder.setTitle("강의실 목록")
        builder.setAdapter(adapter) { _, which ->
            val strName = adapter.getItem(which)!!
            mPresenter.loadTable(strName.replace(" ", ""))
            dismissSelf()
        }
        builder.setPositiveButton("취소") { dialog, _ -> dialog.cancel() }
        builder.show()
    }


}