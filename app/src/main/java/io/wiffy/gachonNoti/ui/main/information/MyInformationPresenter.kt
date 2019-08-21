package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.ui.main.information.idCard.IDCardFragment
import io.wiffy.gachonNoti.ui.main.information.timeTable.TimeTableFragment

class MyInformationPresenter(val mView: MyInformationContract.View) : MyInformationContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    private var idCard: IDCardFragment? = null
    private var timeTable: TimeTableFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) = with(list) {
        idCard = IDCardFragment()
        timeTable = TimeTableFragment()
        add(idCard)
        add(timeTable)
    }

    override fun themeChange() {
        idCard?.changeTheme()
        timeTable?.changeTheme()
    }

    override fun isNotLogin() {
    }

    override fun loginSetting() {
        idCard?.loginInformationSetting(
            StudentInformation(
                getSharedItem("name", "null"),
                getSharedItem("number", "null"),
                getSharedItem("id", "null"),
                getSharedItem("password", "null"),
                getSharedItem("department", "null"),
                getSharedItem("image", "null")
            )
        )
        timeTable?.loginInformationSetting(getSharedItem("number", "null"))
    }
}