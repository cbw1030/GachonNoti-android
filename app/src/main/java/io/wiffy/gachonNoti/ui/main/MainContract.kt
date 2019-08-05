package io.wiffy.gachonNoti.ui.main


import android.app.AlertDialog
import androidx.fragment.app.Fragment

interface MainContract {
    interface View {
        fun changeUI(mList: ArrayList<Fragment?>)
        fun builderUp()
        fun builderDismiss(): Boolean?
        fun makeToast(str: String): Boolean
        fun updatedContents()
        fun setTabText(str: String)
        fun changeTheme()
    }

    interface Presenter {
        fun initPresent()
        fun changeThemes()
        fun resetData(): AlertDialog
    }

}