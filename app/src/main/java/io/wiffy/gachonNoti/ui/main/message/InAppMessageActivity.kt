package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.calculateDateDifference
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.function.setSharedItem
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_inappmessage.*
import java.text.SimpleDateFormat
import java.util.*

@Suppress("DEPRECATION")
class InAppMessageActivity : SuperContract.SuperActivity(), View.OnClickListener {

    lateinit var mAdapter: PagerAdapter
    lateinit var mList: ArrayList<Fragment?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inappmessage)

        setLayoutParams()

        if (calculateDateDifference(getSharedItem("lastDate")) >= 7) {
            setSharedItem("message", true)
        }

        mList = Component.mFragmentList ?: ArrayList()

        mAdapter = PagerAdapter(supportFragmentManager, mList)

        inAppPager.adapter = mAdapter
        inAppPager.offscreenPageLimit = mList.size

        tab_layout.setupWithViewPager(inAppPager)

        val rs = resources.getColor(
            R.color.color_gray
        )

        val state = ColorStateList(
            arrayOf(
                intArrayOf(-android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_checked)
            ), intArrayOf(
                rs, rs
            )
        )

        inAppBox.buttonTintList = state
        inAppBox.setTextColor(state)

        inAppBox.isChecked = !getSharedItem("message", true)

        inAppButton.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {

        setSharedItem(
            "lastDate",
            SimpleDateFormat("yyyy-MM-dd").format(Date(System.currentTimeMillis()))
        )
        setSharedItem("message", !inAppBox.isChecked)

        finish()
        overridePendingTransition(R.anim.not_move_activity, R.anim.abc_fade_out)
    }

    private fun setLayoutParams() {
        try {
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.let {
                window.attributes.run {
                    width = (it.width * 0.8).toInt()
                    height = (it.height * 0.8).toInt()
                }
            }
        } catch (e: Exception) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Component.newActivity = true
    }
}