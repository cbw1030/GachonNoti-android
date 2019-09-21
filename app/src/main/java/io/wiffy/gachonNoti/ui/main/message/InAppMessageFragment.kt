package io.wiffy.gachonNoti.ui.main.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.getRandomColorId
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.fragment_inappmessage.view.*

class InAppMessageFragment(
    private val title: String = "", private val mContext: String = "",
    private val onClick: (() -> (Unit))? = null
) :
    SuperContract.SuperFragment() {

    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_inappmessage, container, false)

        myView.inAppBackground.setBackgroundColor(
            resources.getColor(
                getRandomColorId()
            )
        )

        if (onClick != null) myView.inAppBackground.setOnClickListener { onClick.invoke() }

        myView.inapptitle.text = title
        myView.inappcontext.text = mContext

        return myView
    }

}