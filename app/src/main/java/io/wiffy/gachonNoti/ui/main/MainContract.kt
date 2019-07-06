package io.wiffy.gachonNoti.ui.main


import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        fun changeUI(mList: ArrayList<Fragment>)
        fun builderUp()
        fun builderDismiss()
        fun makeToast(str: String)
        fun updatedContents()
    }

    interface Presenter {
        fun initPresent()
        fun changeThemes()
    }

}