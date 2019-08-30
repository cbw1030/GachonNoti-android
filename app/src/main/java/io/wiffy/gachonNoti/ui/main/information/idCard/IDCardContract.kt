package io.wiffy.gachonNoti.ui.main.information.idCard

import io.wiffy.gachonNoti.model.SuperContract

interface IDCardContract {

    abstract class View : SuperContract.SuperFragment() {
        abstract fun initView()
    }

    interface Presenter : SuperContract.SuperPresenter
}