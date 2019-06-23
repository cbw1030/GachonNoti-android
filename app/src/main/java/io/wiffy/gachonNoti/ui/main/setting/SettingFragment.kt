package io.wiffy.gachonNoti.ui.main.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.ui.main.MainContract

class SettingFragment:Fragment(),MainContract.FragmentSetting {
    var myView: View?=null
    lateinit var mPresenter:SettingPresenter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_setting,container,false)
        mPresenter = SettingPresenter(this)

        return myView
    }
}