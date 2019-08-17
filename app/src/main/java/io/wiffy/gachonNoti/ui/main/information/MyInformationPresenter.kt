package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util.Companion.getSharedItem
import io.wiffy.gachonNoti.ui.main.information.idCard.IDCardFragment

class MyInformationPresenter(val mView: MyInformationContract.View) : MyInformationContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    private var idCard: IDCardFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) = with(list) {
        idCard = IDCardFragment()
        add(idCard)
    }

    override fun themeChange() = idCard?.changeTheme()

    override fun isNotLogin() {
    }

    override fun loginSetting() = idCard?.loginInformationSetting(
        StudentInformation(
            getSharedItem("name", "null"),
            getSharedItem("number", "null"),
            getSharedItem("id", "null"),
            getSharedItem("password", "null"),
            getSharedItem("department", "null"),
            getSharedItem("image", "null")
        )
    )
}