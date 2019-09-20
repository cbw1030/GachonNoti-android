package io.wiffy.gachonNoti.ui.main.message

import android.content.Context
import android.os.Bundle
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.adapter.PagerAdapter

class InAppMessageDialog(context:Context):SuperContract.SuperDialog(context) {

    lateinit var mAdapter:PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_inappmessage)

    }
}