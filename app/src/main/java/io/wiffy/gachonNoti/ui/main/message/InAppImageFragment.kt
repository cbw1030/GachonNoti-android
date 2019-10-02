package io.wiffy.gachonNoti.ui.main.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.utils.getRandomColorId
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.fragment_inappmessage_image.view.*

@Suppress("DEPRECATION")
class InAppImageFragment(
    private val title: String = "",
    private val image: String,
    private val onClick: (() -> (Unit))? = null
) :
    SuperContract.SuperFragment() {

    lateinit var myView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_inappmessage_image, container, false)

        if (onClick != null) {
            myView.inappImage.setOnClickListener { onClick.invoke() }
            myView.inappgo2.visibility = View.VISIBLE
        }

        myView.inAppBackground.setBackgroundColor(
            resources.getColor(
                getRandomColorId()
            )
        )

        Glide.with(this).load(image).into(myView.inappImage)

        return myView
    }

}