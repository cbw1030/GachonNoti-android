package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.information.idCard.ExampleFragment

class IDCardPresenter(val mView: IDCardContract.View) : IDCardContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    private var example: ExampleFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) {
        with(list) {
            example = ExampleFragment()
            add(example)
        }
    }

    override fun themeChange() {
        if (example != null) example?.changeTheme()
    }

    override fun isNotLogin() {

    }

    override fun loginSetting() {
        with(Util.sharedPreferences)
        {
            if (example != null) example?.loginInformationSetting(
              StudentInformation(
                  getString("name","null")?:"null",
                  getString("number","null")?:"null",
                  getString("id","null")?:"null",
                  getString("password","null")?:"null",
                  getString("department","null")?:"null",
                  getString("image","null")?:"null"
              )
            )
        }
    }
}