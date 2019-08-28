package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.isNetworkConnected
import io.wiffy.gachonNoti.`object`.Component
import kotlinx.android.synthetic.main.dialog_search.*
import java.lang.Exception
import kotlin.collections.ArrayList


class SearchDialog(
    context: Context,
    private val mPresenter: SearcherPresenter
) : SearchContract.DialogPresenter(context) {

    var spinnerSelected = 0

    @SuppressLint("ApplySharedPref", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search)

        mPresenter.initPresentDialog(this)
        year.text = "[${if (Component.campus) {
            "글로벌"
        } else {
            "메디컬"
        }}] ${Component.YEAR}년도 ${when (Component.SEMESTER) {
            1 -> "1"
            2 -> "2"
            3 -> "여름"
            else -> "겨울"
        }
        }학기"


        getdata.setOnClickListener {
            getDataDialog("${Component.YEAR}-${Component.SEMESTER}")
        }

        search.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                Component.getBuilder()?.show()
            }
            try {
                mPresenter.loadRoom(cate.getItemAtPosition(spinnerSelected).toString())
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post {
                    Component.getBuilder()?.dismiss()
                }
            }
        }

        cate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinnerSelected = position
            }
        }

        mPresenter.isDownloaded(Component.YEAR, Component.SEMESTER.toString())
    }

    override fun categoryInvisible() {
        cate.visibility = View.INVISIBLE
    }

    override fun dismissSelf() {
        dismiss()
    }

    override fun getDataDialog(yearSemester: String) {
        if (isNetworkConnected(context)) {
            AlertDialog.Builder(context).apply {
                setTitle("시간표 데이터를 가져옵니다.")
                setMessage("시간이 다소 걸릴 수 있으니 중간에 앱을 종료하지 마세요.\n(최초 한번만 다운로드 합니다.)")
                setPositiveButton("OK") { _, _ ->
                    mPresenter.getData(yearSemester)
                }
            }.show()
        } else {
            toast("인터넷 연결을 확인해주세요.")
        }
    }

    override fun errorDialog() {
        AlertDialog.Builder(context).apply {
            setTitle("오류")
            setMessage("시간표 데이터가 없습니다.")
            setPositiveButton(
                "OK"
            ) { _, _ -> }
        }.show()
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

    override fun requestLoad() =
        mPresenter.isDownloaded(Component.YEAR, Component.SEMESTER.toString())

    override fun setSpinner(arrayList: ArrayList<String>) {
        cate.visibility = View.VISIBLE
        cate.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayList)
    }

    override fun setListDialog(arrayList: ArrayList<String>) {
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1).apply {
            for (x in arrayList) {
                add(x)
            }
        }
        AlertDialog.Builder(context).apply {
            setTitle("강의실 목록")
            setAdapter(adapter) { _, which ->
                mPresenter.loadTable(adapter.getItem(which)!!.replace(" ", ""))
                dismissSelf()
            }
            setPositiveButton("취소") { dialog, _ -> dialog.cancel() }
        }.show()
    }
}