package io.wiffy.gachonNoti.ui.main.setting.contact

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.getThemeButtonResource
import io.wiffy.gachonNoti.function.isNetworkConnected
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.setting.SettingContract
import kotlinx.android.synthetic.main.dialog_contact.*

class ContactDialog(context: Context, private val mView: SettingContract.View) :
    SuperContract.SuperDialog(context) {
    lateinit var myList: ArrayList<String>
    var spinnerSelected = 0
    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contact)

        myList = arrayListOf("이름", "소속")

        cate2.setPadding(0, 0, 5, 0)
        cate2.adapter = ArrayAdapter(context, R.layout.my_spinner, myList)
        cate2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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
        search2.setBackgroundResource(getThemeButtonResource())
        search2.setOnClickListener {
            if (isNetworkConnected(context)) {
                val text = editor1.text.toString()
                val isWiffy =
                    ("wiffy".contains(text.toLowerCase()) || text.toLowerCase().contains("wiffy")) && spinnerSelected == 1

                if (text.isNotBlank()) {
                    mView.executeTask(
                        when (spinnerSelected) {
                            1 -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=dept&searchword=$text"
                            else -> "http://m.gachon.ac.kr/number/index.jsp?search=1&searchopt=name&searchword=$text"
                        },
                        (("박상현".contains(text) || text.contains("박상현")) && spinnerSelected == 0) || isWiffy,
                        (("박정호".contains(text) || text.contains("박정호")) && spinnerSelected == 0) || isWiffy
                    )
                    dismiss()
                }
            } else {
                toast("인터넷 연결을 확인해주세요.")
            }
        }
    }
}