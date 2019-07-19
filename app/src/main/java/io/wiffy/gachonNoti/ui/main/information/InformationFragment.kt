package io.wiffy.gachonNoti.ui.main.information

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.PagerAdapter
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.fragment_information.view.*
import kotlinx.android.synthetic.main.fragment_notification.view.*

class InformationFragment:Fragment(),InformationContract.View {
    lateinit var myView: View
    lateinit var mPresenter: InformationPresenter
    lateinit var adapter: PagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         myView = inflater.inflate(R.layout.fragment_information, container, false)

        return myView
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dismissLoad() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
    fun themeChanger(bool: Boolean) {
        val themeColorArray = arrayOf(
            intArrayOf(-android.R.attr.state_checked),
            intArrayOf(android.R.attr.state_checked)
        )
        val color = when (Util.theme) {
            "red" -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.red)

            )
            "green" -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.green)

            )
            else -> intArrayOf(
                Color.parseColor("#8A8989"),
                resources.getColor(R.color.main2Blue)

            )
        }
        if (bool)
            mPresenter.themeChange()
        myView.navigation3.tabTextColors = ColorStateList(themeColorArray, color)
        myView.navigation3.setSelectedTabIndicatorColor(color[1])

    }
}