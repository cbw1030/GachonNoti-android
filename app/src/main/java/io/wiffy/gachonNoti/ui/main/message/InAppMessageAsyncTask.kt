package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.function.ACTION_SUCCESS
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.MainContract

@SuppressLint("StaticFieldLeak")
class InAppMessageAsyncTask( private val mView:MainContract.View):SuperContract.SuperAsyncTask<Void,Void,Int>() {

    private val mList = ArrayList<Fragment?>()

    override fun doInBackground(vararg params: Void?): Int {

        mList.add(InAppMessageFragment())
        mList.add(InAppMessageFragment())
        mList.add(InAppMessageFragment())

        return ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        when(result)
        {
            ACTION_SUCCESS->{
                mView.setMessage(mList)
            }
        }
    }
}