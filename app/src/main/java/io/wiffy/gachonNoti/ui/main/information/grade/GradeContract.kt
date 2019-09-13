package io.wiffy.gachonNoti.ui.main.information.grade

import android.content.Context
import io.wiffy.gachonNoti.model.CreditAverage
import io.wiffy.gachonNoti.model.CreditFormal
import io.wiffy.gachonNoti.model.SuperContract

interface GradeContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
        abstract fun sendContext(): Context?
        abstract fun setView(avg: CreditAverage?, list: ArrayList<CreditFormal>)
        abstract fun setSpinner(
            list1: ArrayList<String>,
            list2: ArrayList<String>,
            list3: ArrayList<String>
        )
    }

    interface Presenter : SuperContract.SuperPresenter {
        fun patternCheck()
    }
}