package io.wiffy.gachonNoti.ui.main.information.grade

import io.wiffy.gachonNoti.model.SuperContract

interface GradeContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
    }

    interface Presenter : SuperContract.WiffyObject {
        fun initPresent()
    }
}