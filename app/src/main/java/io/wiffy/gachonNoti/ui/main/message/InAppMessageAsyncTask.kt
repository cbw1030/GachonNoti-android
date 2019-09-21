package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.ACTION_SUCCESS
import io.wiffy.gachonNoti.function.getSharedItem
import io.wiffy.gachonNoti.function.setSharedItem
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.MainContract

@SuppressLint("StaticFieldLeak")
class InAppMessageAsyncTask(
    private val mView: MainContract.View,
    private val flag: Boolean = true
) : SuperContract.SuperAsyncTask<Void, Void, Int>() {

    private val mList = ArrayList<Fragment?>()

    override fun doInBackground(vararg params: Void?): Int {

        if ((!flag || !getSharedItem(Component.version, false))) {
            mList.add(
                InAppMessageFragment(
                    "${Component.version} 버전 업데이트", mView.sendContext().resources.getString(
                        R.string.update
                    )
                )
            )
            setSharedItem(Component.version, true)
        }

        mList.add(
            InAppMessageFragment(
                "기모찌", "눌러봐라~~"
            ) { mView.linkToSite("https://www.naver.com") }
        )


        // TODO 서버에서 알림사항을 가져올 수 있음

        return ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        when (result) {
            ACTION_SUCCESS -> {
                mView.setMessage(mList, flag)
            }
        }
    }
}