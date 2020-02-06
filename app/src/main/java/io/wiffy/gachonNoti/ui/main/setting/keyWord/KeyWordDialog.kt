package io.wiffy.gachonNoti.ui.main.setting.keyWord

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.`object`.Component
import io.wiffy.gachonNoti.model.`object`.VerticalSpaceItemDecoration
import io.wiffy.gachonNoti.model.adapter.KeyWordAdapter
import io.wiffy.gachonNoti.utils.*
import kotlinx.android.synthetic.main.dialog_keyword.*

class KeyWordDialog(context: Context) : SuperContract.SuperDialog(context) {

    var use = getSharedItem("notiKey", false)
    val list = getSharedItem<HashSet<String>>("notiKeySet").toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_keyword)

        keySwitch.isChecked = use
        addKeyword.setOnClickListener {
            onClick()
        }
        keySwitch.setOnCheckedChangeListener { _, isChecked ->
            setEnabled(isChecked)

            if (isChecked) {
                toastLong("키워드가 포함되지 않은\n알림은 무시됩니다.")
            }
            use = isChecked
            setSharedItem("notiKey", isChecked)
        }

        //list가 비어있으면 KeyWordAdapter에 list가 안넘어가는거같음
        //필요없는 데이터 "%nodata%" 넣고 뒤에서 remove해주면 일다 해결됨
        if (list.isEmpty()) {
            list.add(0, "%nodata%")
        }

        keywordRecycler.adapter = KeyWordAdapter(list)
        keywordRecycler.layoutManager = LinearLayoutManager(context)
        keywordRecycler.addItemDecoration(
            VerticalSpaceItemDecoration
        )
        setTheme()
        setEnabled(use)
    }

    private fun onClick() {
        val msg = keywordEdit.text.toString()

        if (msg.isNotBlank()) {
            if (list.contains(msg)) {
                toast("이미 존재하는 키워드입니다.")
                return
            }

            list.add(0, msg)
            keywordRecycler.adapter?.notifyItemInserted(0)

            keywordEdit.setText("")

            setSharedItem("notiKeySet", list.toHashSet())
            //toast("${getSharedItem<HashSet<String>>("notiKeySet").toMutableList().size}")
        } else {
            toast("키워드를 입력해주세요.")
        }
    }

    private fun setTheme() {

        if (Component.darkTheme) {
            keyword.setBackgroundColor(context.resources.getColor(getDarkColor2()))
        } else {
            keyword.setBackgroundColor(context.resources.getColor(getThemeColor()))
        }
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
        if (bool) {
            frames.visibility = View.VISIBLE
            addKeyword.setBackgroundResource(getThemeButtonResource())

            if (list.isEmpty()) {
                keywordRecycler.visibility = View.GONE
                noKey.visibility = View.VISIBLE
            } else {
                keywordRecycler.visibility = View.VISIBLE
                noKey.visibility = View.GONE
            }

            if (list.contains("%nodata%")) {
                list.removeAt(0)
                keywordRecycler.adapter?.notifyItemInserted(0)
            }
        } else {
            frames.visibility = View.GONE
            addKeyword.setBackgroundColor(context.resources.getColor(R.color.gray2))

            if (list.isEmpty()) {
                list.add(0, "%nodata%")
                keywordRecycler.adapter?.notifyItemInserted(0)
            }
        }
    }
}