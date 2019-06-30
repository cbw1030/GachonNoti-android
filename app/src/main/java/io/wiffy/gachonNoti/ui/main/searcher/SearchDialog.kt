package io.wiffy.gachonNoti.ui.main.searcher

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.activity_detailsetting.*
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.fragment_searcher.view.*
import java.util.*
import kotlin.collections.ArrayList


class SearchDialog(context: Context,
                   private val mView: MainContract.FragmentSearcher,
                   private val mPresenter:MainContract.PresenterSearcher) : Dialog(context),MainContract.PresenterSearchDialog  {

    var spinnerSelected = 0
    lateinit var yearr :String
    lateinit var semester: String

    @SuppressLint("ApplySharedPref")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_search)

        mPresenter.initPresentDialog(this)

        yearr = Calendar.getInstance().get(Calendar.YEAR).toString()
        val month = Calendar.getInstance().get(Calendar.MONTH)
        year.setText(
            when {
                month <= 7 -> {
                    semester = "1"
                    "$yearr 1학기"
                }
                else -> {
                    semester = "2"
                    "$yearr 2학기"
                }
            }
        )

        getdata.setOnClickListener {
            getDataDialog("$yearr-$semester")
        }

        search.setOnClickListener {
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


    override fun getDataDialog(yearSemester: String) {
        val builder = AlertDialog.Builder(context, R.style.light_dialog)
        builder.setTitle("시간표 데이터를 가져옵니다.")
        builder.setMessage("시간이 다소 걸릴 수 있으니 중간에 앱을 종료하지 마세요.")
        builder.setPositiveButton("OK") { _, _ ->
            mPresenter.getData(yearSemester)
        }
        builder.show()
    }

    override fun errorDialog() {
        val builder = AlertDialog.Builder(context, R.style.light_dialog)
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

    override fun requestLoad(){
        mPresenter.isDownloaded(yearr, semester)
    }

    override fun setSpinner(arrayList: ArrayList<String>) {
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, arrayList)
        cate.adapter = arrayAdapter
    }

    override fun setlistDialog(arrayList: ArrayList<String>) {
        val builder = AlertDialog.Builder(context)
        val adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1)
        for (x in arrayList) {
            adapter.add(x)
        }
        builder.setTitle("이용가능한 강의실")
        builder.setAdapter(adapter, DialogInterface.OnClickListener { _, which ->
            var strName = adapter.getItem(which)
            mPresenter.loadTable(strName.replace(" ",""))
            //Toast.makeText(activity, strName, Toast.LENGTH_SHORT).show()
        })
        builder.setPositiveButton("취소") { dialog, _ -> dialog.cancel() }
        builder.show()

    }


}