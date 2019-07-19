package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.ui.main.information.example.ExampleFragment

class InformationPresenter(val mView: InformationContract.View) : InformationContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    var example: ExampleFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) {
       with(list){
           example = ExampleFragment()
           add(example)
       }
    }

    override fun themeChange() {
       if(example!=null)example?.changeTheme()
    }
}