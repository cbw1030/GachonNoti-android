package io.wiffy.gachonNoti.ui.main.message

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import com.skydoves.whatif.whatIfNotNull
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.function.*
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.ui.main.MainContract
import org.json.JSONObject
import java.net.URL
import kotlin.collections.ArrayList

@SuppressLint("StaticFieldLeak")
class InAppMessageAsyncTask(
    private val mView: MainContract.View,
    private val flag: Boolean = true
) : SuperContract.SuperAsyncTask<Void, Void, Int>() {

    private val mList = ArrayList<Fragment?>()
    private val url = "https://palecosmos.github.io/noti/alimi/"

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

        Component.myObject.whatIfNotNull(whatIf = {
            setFragmentList()
        }, whatIfNot = {
            setJsonObject()
        })

        return ACTION_SUCCESS
    }

    override fun onPostExecute(result: Int?) {
        when (result) {
            ACTION_SUCCESS -> {
                mView.setMessage(mList, flag)
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setFragmentList() {
        Component.myObject?.let {
            val count = it.getInt("count")
            val obj = it.getJSONArray("request")

            for (n in 0 until count) {

                if (calculateDateIntegerDifference(obj.getJSONObject(n).getString("exp")) < 0) {
                    continue
                }

                when (obj.getJSONObject(n).getString("type")) {
                    "text" -> {
                        mList.add(
                            InAppMessageFragment(
                                obj.getJSONObject(n).getString("title"),
                                obj.getJSONObject(n).getString("context")
                            )
                        )
                    }
                    "link" -> {
                        mList.add(
                            InAppMessageFragment(
                                obj.getJSONObject(n).getString("title"),
                                obj.getJSONObject(n).getString("context")
                            ) { mView.linkToSite(obj.getJSONObject(n).getString("url")) }
                        )
                    }
                    "image" -> {
                        mList.add(
                            InAppImageFragment(
                                obj.getJSONObject(n).getString("title"),
                                obj.getJSONObject(n).getString("image")
                            ) { mView.linkToSite(obj.getJSONObject(n).getString("url")) }
                        )
                    }
                }
            }
        }
    }

    private fun setJsonObject() {
        try {
            Component.myObject = JSONObject(URL(url).readText())
        } catch (e: Exception) {
        }
        setFragmentList()
    }
}