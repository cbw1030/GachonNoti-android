package io.wiffy.gachonNoti.ui.main.information.credit

import android.content.Context
import io.wiffy.gachonNoti.model.CreditInformation
import io.wiffy.gachonNoti.model.SuperContract

interface CreditContract {
    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
        abstract fun sendContext(): Context?
        abstract fun initList(list: ArrayList<CreditInformation>, cmd: String)
    }

    interface Presenter : SuperContract.SuperPresenter {
        fun setLogin(info: String)
    }
}