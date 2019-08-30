package io.wiffy.gachonNoti.ui.main.setting.administrator

import android.content.Context
import android.os.Bundle
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.func.*
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.dialog_administrator.*
import java.util.*

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
        if (getSharedItem("yearAuto", true)) {
            option_year.setText(getSharedItem<String>("year"))
            yearAuto.isChecked = false
        } else {
            option_year.setText(Component.YEAR)
            yearAuto.isChecked = true
        }
        if (getSharedItem("semesterAuto", true)) {
            option_autoSeme.isChecked = true
        } else {
            when (getSharedItem<Int>("semester")) {
                1 -> option_1
                2 -> option_2
                3 -> option_summer
                else -> option_winter
            }.isChecked = true
        }
        yearAuto.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                option_year.setText(Calendar.getInstance().get(Calendar.YEAR).toString())
                option_year.isClickable = false
            } else {
                option_year.isClickable = true
            }
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
        setSharedItems(
            Pair("gender", option_male.isChecked),
            Pair("yearAuto", yearAuto.isChecked),
            Pair("semesterAuto", option_autoSeme.isChecked)
        )
        setSharedItems(
            Pair("year", option_year.text.toString().trim().toInt()),
            Pair("semester", when ())
        )
        dismiss()
        restartApp(context)
    }
}