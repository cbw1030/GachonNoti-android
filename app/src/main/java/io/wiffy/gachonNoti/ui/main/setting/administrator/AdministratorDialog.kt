package io.wiffy.gachonNoti.ui.main.setting.administrator

import android.content.Context
import android.os.Bundle
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.dialog_administrator.*

class AdministratorDialog(context: Context) : SuperContract.SuperDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_administrator)
        initView()
    }

    private fun initView() {
        if (getSharedItem("gender")) {
            option_male.isChecked = true
        } else {
            option_female.isChecked = true
        }

        lefts.run {
            setBackgroundResource(getThemeButtonResource())
            setOnClickListener {
                setValue()
            }
        }
        rights.run {
            setBackgroundResource(getThemeButtonResource())
            setOnClickListener {
                dismiss()
            }
        }
        option_name.run {
            setText(getSharedItem<String>("name"))
        }
        option_depart.run {
            setText(getSharedItem<String>("department"))
        }
        option_number.run {
            setText(getSharedItem<String>("number"))
        }
        option_birthday.run {
            setText(getSharedItem<String>("birthday"))
        }

    }

    override fun onBackPressed() = dismiss()

    private fun setValue() {
        setSharedItems(
            Pair("number", option_number.text.toString().trim()),
            Pair("department", option_depart.text.toString().trim()),
            Pair("name", option_name.text.toString().trim()),
            Pair("birthday", option_birthday.text.toString().trim()),
            Pair(
                "image",
                "http://gcis.gachon.ac.kr/common/picture/haksa/shj/${option_number.text}.jpg"
            )
        )
        setSharedItem("gender", option_male.isChecked)
        dismiss()
        restartApp(context)
    }
}