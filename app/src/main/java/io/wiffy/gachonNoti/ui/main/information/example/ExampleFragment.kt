package io.wiffy.gachonNoti.ui.main.information.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R

class ExampleFragment:Fragment(),ExampleContract.View {
    lateinit var myView: View
    lateinit var mPresenter:ExamplePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_example, container, false)
        mPresenter = ExamplePresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun initView() {

    }

    fun changeTheme()
    {

    }
}