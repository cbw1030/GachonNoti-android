package io.wiffy.gachonNoti.ui.main.setting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_report.*

class ReportDialog(context: Context, private val mView: SettingContract.View) : Dialog(context) {
    var spinnerSelected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_report)

        cate3.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, listOf("버그리포트", "개선사항"))
        cate3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                spinnerSelected = position
            }
        }
        search3.setOnClickListener {
            if (Util.isNetworkConnected(context)) {
                val text = editor2_text.text.toString().replace(":",";")
                if (text.isNotBlank()) {
                    val query =
                        when (spinnerSelected) {
                            1 -> "개선사항:$text"
                            else -> "버그리포트:$text"
                        }
                    mView.executeTask2(query)
                    dismiss()
                }
            } else {
                Toast.makeText(context, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}