package io.wiffy.gachonNoti.ui.main.setting.administrator

import android.content.Context
import android.os.Bundle
import android.text.method.KeyListener
import io.wiffy.extension.restartApp
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.utils.*
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.dialog_administrator.*
import java.util.*

class AdministratorDialog(context: Context) : SuperContract.SuperDialog(context, R.style.mStyle) {

    lateinit var keyListener: KeyListener

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

        keyListener = option_year.keyListener

        if (getSharedItem("yearAuto", true)) {
            option_year.setText(Calendar.getInstance().get(Calendar.YEAR).toString())
            option_year.keyListener = null
            yearAuto.isChecked = true
        } else {
            option_year.setText(getSharedItem<Int>("year").toString())
            yearAuto.isChecked = false
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
        yearAuto.setOnCheckedChangeListener { _, check ->
            option_year.let {
                if (check) {
                    it.setText(Calendar.getInstance().get(Calendar.YEAR).toString())
                    it.keyListener = null
                } else {
                    it.keyListener = keyListener
                }
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
            Pair(
                "semester", when (option_semester.checkedRadioButtonId) {
                    R.id.option_1 -> 1
                    R.id.option_2 -> 2
                    R.id.option_summer -> 3
                    R.id.option_winter -> 4
                    else -> 0
                }
            )
        )
        dismiss()
        restartApp(context)
    }
}