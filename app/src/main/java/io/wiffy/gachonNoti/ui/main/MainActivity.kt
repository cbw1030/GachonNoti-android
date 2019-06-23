package io.wiffy.gachonNoti.ui.main

import android.content.Context
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mPresenter = MainPresenter(this)
        mPresenter.initPresent()

    }

    override fun changeUI(mList: ArrayList<Fragment>) {
        adapter = PagerAdapter(supportFragmentManager, mList)
        navigation.addTab(navigation.newTab().setText("공지"))
        navigation.addTab(navigation.newTab().setText("설정"))

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
//        navigation.setupWithViewPager(pager)
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(
            Util.wrap(
                newBase,
                Util.global
            )
        )
    }
}