package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.setSharedItem
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import kotlinx.android.synthetic.main.activity_inappmessage.*
import java.text.SimpleDateFormat
import java.util.*

class InAppMessageActivity : SuperContract.SuperActivity(), View.OnClickListener {

    lateinit var mAdapter: PagerAdapter
    lateinit var mList: ArrayList<Fragment?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inappmessage)

        setLayoutParams()

        mList = Component.mFragmentList ?: ArrayList()

        mAdapter = PagerAdapter(supportFragmentManager, mList)

        inAppPager.adapter = mAdapter
        inAppPager.offscreenPageLimit = mList.size

        if (!intent.getBooleanExtra("isOpen", true)) {
            inAppBox.visibility = View.GONE
            inAppBox2.visibility = View.GONE
        }

        inAppButton.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {
        if (inAppBox.isChecked) {
            setSharedItem(
                "lastDate",
                SimpleDateFormat("yyyy-mm-dd").format(Date(System.currentTimeMillis()))
            )
            setSharedItem("message", false)
        }
        finish()
    }

    private fun setLayoutParams() {
        try {
            (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.let {
                window.attributes.run {
                    width = (it.width * 0.7).toInt()
                    height = (it.height * 0.7).toInt()
                }
            }
        } catch (e: Exception) {
        }
    }

}