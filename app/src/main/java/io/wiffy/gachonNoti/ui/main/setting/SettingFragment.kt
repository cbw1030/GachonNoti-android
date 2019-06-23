package io.wiffy.gachonNoti.ui.main.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainContract
import kotlinx.android.synthetic.main.fragment_setting.*
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment:Fragment(),MainContract.FragmentSetting {
    lateinit var myView: View
    lateinit var mPresenter:SettingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_setting,container,false)
        mPresenter = SettingPresenter(this)
        mPresenter.initPresent()

        return myView
    }

    override fun addListener(listener1: View.OnClickListener) {
        myView.lang.setOnClickListener(listener1)
    }

    override fun languageSetting() {
        activity?.startActivityForResult(Intent(context,LanguageActivity::class.java),1)
        activity?.overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out)
    }
}