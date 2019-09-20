package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.setSharedItem
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.adapter.PagerAdapter
import kotlinx.android.synthetic.main.dialog_inappmessage.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class InAppMessageDialog(
    private val activity: AppCompatActivity,
    private val mList: ArrayList<Fragment?>
) : SuperContract.SuperDialog(activity), View.OnClickListener {

    lateinit var mAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_inappmessage)

        mAdapter = PagerAdapter(activity.supportFragmentManager, mList)
        inAppPager.adapter = mAdapter
        inAppPager.offscreenPageLimit = mList.size

        inAppButton.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {
        if(inAppBox.isChecked)
        {
            setSharedItem("lastDate",
                SimpleDateFormat("yyyy-mm-dd").format(Date(System.currentTimeMillis())))
            setSharedItem("message", false)
        }
        dismiss()
    }

}