package io.wiffy.gachonNoti.ui.main.information.credit

import io.wiffy.gachonNoti.model.SuperContract

class CreditAsyncTask(val mView: CreditContract.View, val number: String) :
    SuperContract.SuperAsyncTask<Void, Void, Int>() {

    override fun doInBackground(vararg params: Void?): Int {
        return 0
    }

}