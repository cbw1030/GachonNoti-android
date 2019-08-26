package io.wiffy.gachonNoti.ui.main.information

import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.func.getSharedItem
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.ui.main.information.credit.CreditFragment
import io.wiffy.gachonNoti.ui.main.information.grade.GradeFragment
import io.wiffy.gachonNoti.ui.main.information.idCard.IDCardFragment
import io.wiffy.gachonNoti.ui.main.information.timeTable.TimeTableFragment

class MyInformationPresenter(val mView: MyInformationContract.View) : MyInformationContract.Presenter {
    override fun initPresent() {
        mView.initView()
    }

    private var idCard: IDCardFragment? = null
    private var timeTable: TimeTableFragment? = null
    private var grade: GradeFragment? = null
    private var credit: CreditFragment? = null

    override fun fragmentInflation(list: ArrayList<Fragment?>) = with(list) {
        idCard = IDCardFragment()
        timeTable = TimeTableFragment()
        grade = GradeFragment()
        credit = CreditFragment()
        add(idCard)
        add(timeTable)
        add(grade)
        add(credit)
    }

    override fun themeChange() {
        idCard?.changeTheme()
        timeTable?.changeTheme()
        grade?.changeTheme()
        credit?.changeTheme()
    }

    override fun resetTable() = timeTable?.resetTable()

    override fun isNotLogin() {
    }

    override fun patternCheck() = grade?.patternCheck()

    override fun setPatternVisibility() = grade?.setViewVisibility(false)

    override fun loginSetting() {
        idCard?.loginInformationSetting(
            StudentInformation(
                getSharedItem("name", "null"),
                getSharedItem("number", "null"),
                getSharedItem("id", "null"),
                getSharedItem("password", "null"),
                getSharedItem("department", "null"),
                getSharedItem("image", "null"),
                getSharedItem("clubCD", "null")
            )
        )
        timeTable?.loginInformationSetting(getSharedItem("number", "null"))
        credit?.loginInformationSetting(getSharedItem("number", "null"))
        grade?.loginInformationSetting(getSharedItem("number", "null"))
    }
}