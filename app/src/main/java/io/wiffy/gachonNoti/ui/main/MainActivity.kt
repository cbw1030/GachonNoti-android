package io.wiffy.gachonNoti.ui.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var mPresenter: MainPresenter
    lateinit var adapter: PagerAdapter
    lateinit var parseList: ArrayList<String>
    lateinit var builder: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mPresenter = MainPresenter(this)
        parseList = ArrayList()
        builder = Dialog(this)
        builder.setContentView(R.layout.builder)
        builder.setCancelable(false)
        builder.setCanceledOnTouchOutside(false)
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
        MainAsyncTask(parseList, this)
    }

    override fun init() {
        mPresenter.initPresent()
    }

    override fun builderUp() {
        builder.show()
    }

    override fun getList(): ArrayList<String> = parseList

    override fun builderDismiss() {
        builder.dismiss()
    }

    override fun changeUI(mList: ArrayList<Fragment>) {
        adapter = PagerAdapter(supportFragmentManager, mList)
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Notification)))
        navigation.addTab(navigation.newTab().setText(resources.getString(R.string.Setting)))
        pager.adapter = adapter
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(navigation))
        navigation.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                pager.currentItem = tab?.position!!
            }
        })

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            Util.wrap(
                newBase,
                Util.global
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Util.languageResultOn -> Util.restartApp(applicationContext)

            Util.languageResultOff -> {
            }
            else -> {
                finish()
            }
        }
    }
}