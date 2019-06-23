package io.wiffy.gachonNoti.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import io.wiffy.gachonNoti.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {
    lateinit var mPresenter: MainPresenter
    lateinit var adapter:PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter(this)
        mPresenter.initPresent()

    }

    override fun changeUI(mList:ArrayList<Fragment>) {
        adapter = PagerAdapter(supportFragmentManager,mList)
        navigation.addTab(navigation.newTab().setText("공지"))
        navigation.addTab(navigation.newTab().setText("설정"))
        pager.adapter = adapter
//        pager.addOnPageChangeListener(object:TabLayout.TabLayoutOnPageChangeListener(navigation))
//        navigation.setOnTabSelectedListener()

    }
}