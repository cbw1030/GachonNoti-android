package io.wiffy.gachonNoti.ui.main.setting.administrator

import android.content.Context
import android.os.Bundle
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.func.getThemeButtonResource
import io.wiffy.gachonNoti.func.restartApp
import io.wiffy.gachonNoti.func.setSharedItems
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.dialog_administrator.*

class AdministratorDialog(context: Context) : SuperContract.SuperDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_administrator)
        initView()
    }

    private fun initView() {
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
        option_number.run {
            text = getSharedItem("number")
        }
        option_birthday.run {
            text = getSharedItem("birthday")
        }
    }

    override fun onBackPressed() = dismiss()

    private fun setValue() {
        setSharedItems(
            Pair("number", option_number.text.trim()),
            Pair("birthday", option_birthday.text.trim())
        )
        dismiss()
        restartApp(context)
    }
}