package io.wiffy.gachonNoti.ui.main.information.grade

import io.wiffy.gachonNoti.model.SuperContract

class GradeAsyncTask(val mView: GradeContract.View, val number: String) :
    SuperContract.SuperAsyncTask<Void, Void, Int>() {
    override fun doInBackground(vararg params: Void?): Int {
        return 0
    }
}