package io.wiffy.gachonNoti.ui.main.setting.keyWord

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.Toast
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.`object`.Component
import io.wiffy.gachonNoti.utils.*
import kotlinx.android.synthetic.main.dialog_keyword.*

class KeyWordDialog(context: Context) : SuperContract.SuperDialog(context) {

    var use = getSharedItem("notiKey", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_keyword)

        keySwitch.isChecked = use
        addKeyword.setOnClickListener {
            //Toast.makeText(context,"df",Toast.LENGTH_LONG)
        }
        keySwitch.setOnCheckedChangeListener { _, isChecked ->
            setEnabled(isChecked)

            if(isChecked)
            {
                toastLong("키워드가 포함되지 않은\n알림은 무시됩니다.")
            }

            setSharedItem("notiKey", isChecked)
        }

        setTheme()
        setEnabled(use)
    }

    private fun setTheme() {

        keyword.setBackgroundColor(context.resources.getColor(getThemeColor()))
        addKeyword.setBackgroundResource(getThemeButtonResource())

        arrayOf(
            intArrayOf(
                android.R.attr.state_checked
            ),
            intArrayOf(-android.R.attr.state_checked)
        ).also {
            keySwitch.apply {
                thumbTintList =
                    ColorStateList(
                        it,
                        intArrayOf(
                            resources.getColor(
                                if (Component.darkTheme) {
                                    getDarkColor1()
                                } else {
                                    getThemeColor()
                                }
                            ),
                            resources.getColor(R.color.gray2)
                        )
                    )
                trackTintList = ColorStateList(
                    it, intArrayOf(
                        resources.getColor(
                            if (Component.darkTheme) {
                                getDarkLightColor()
                            } else {
                                getThemeLightColor()
                            }
                        ), resources.getColor(R.color.lightGray)
                    )
                )
            }
        }
    }

    private fun setEnabled(bool: Boolean) {
        keywordEdit.isEnabled = bool
        addKeyword.isEnabled = bool
        if(bool)
        {
            addKeyword.setBackgroundResource(getThemeButtonResource())
        }else{
            addKeyword.setBackgroundColor(context.resources.getColor(R.color.gray2))
        }
    }
}