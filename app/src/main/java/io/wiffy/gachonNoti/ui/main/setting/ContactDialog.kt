package io.wiffy.gachonNoti.ui.main.setting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.wiffy.gachonNoti.R
import kotlinx.android.synthetic.main.dialog_contact.*

class ContactDialog(context: Context, private val mView: SettingContract.View) : Dialog(context) {
    lateinit var myList: ArrayList<String>
    var spinnerSelected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contact)

        myList = ArrayList()
        myList.add("이름")
        myList.add("소속")
        cate2.setPadding(0, 0, 5, 0)
        cate2.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, myList)
        cate2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerSelected = position
            }
        }
        search2.setOnClickListener {
            val text = editor1.text.toString()
            if (text.isNotBlank()) {
                val query =
                    when (spinnerSelected) {
                        1 -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=dept&searchword=$text"

                        else -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=name&searchword=$text"
                    }
                mView.executeTask(query)
                dismiss()
            }
        }
    }
}