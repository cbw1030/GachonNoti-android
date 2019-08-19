package io.wiffy.gachonNoti.ui.main.setting.contact

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.isNetworkConnected
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.setting.SettingContract
import kotlinx.android.synthetic.main.dialog_contact.*

class ContactDialog(context: Context, private val mView: SettingContract.View) : SuperContract.SuperDialog(context) {
    lateinit var myList: ArrayList<String>
    var spinnerSelected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contact)

        myList = ArrayList<String>().apply {
            add("이름")
            add("소속")
        }

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
            if (isNetworkConnected(context)) {
                val text = editor1.text.toString()
                if (text.isNotBlank()) {
                    if ("박상현".contains(text) && spinnerSelected == 0) {
                        mView.builderUp()
                        ArrayList<ContactInformation>().apply {
                            add(ContactInformation("천재", "박상현", "201735829"))
                            mView.builderDismissAndContactUp(this)
                        }
                    } else if ("박정호".contains(text) && spinnerSelected == 0) {
                        mView.builderUp()
                        ArrayList<ContactInformation>().apply {
                            add(ContactInformation("바보", "박정호", "201635812"))
                            mView.builderDismissAndContactUp(this)
                        }
                    } else {
                        val query =
                            when (spinnerSelected) {
                                1 -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=dept&searchword=$text"

                                else -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=name&searchword=$text"
                            }
                        mView.executeTask(query)
                        dismiss()
                    }
                }
            } else {
                toast("인터넷 연결을 확인해주세요.")
            }
        }
    }
}