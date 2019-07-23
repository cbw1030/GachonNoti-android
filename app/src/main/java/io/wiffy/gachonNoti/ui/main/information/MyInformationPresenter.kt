package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.data.StudentInformation
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.main.information.idCard.IDCardFragment

class MyInformationPresenter(val mView: MyInformationContract.View) : MyInformationContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    private var idCard: IDCardFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) {
        with(list) {
            idCard = IDCardFragment()
            add(idCard)
        }
    }

    override fun themeChange() {
        if (idCard != null) idCard?.changeTheme()
    }

    override fun isNotLogin() {

    }

    override fun loginSetting() {
        with(Util.sharedPreferences)
        {
            if (idCard != null) idCard?.loginInformationSetting(
                StudentInformation(
                    getString("name", "null") ?: "null",
                    getString("number", "null") ?: "null",
                    getString("id", "null") ?: "null",
                    getString("password", "null") ?: "null",
                    getString("department", "null") ?: "null",
                    getString("image", "null") ?: "null"
                )
            )
        }
    }
}