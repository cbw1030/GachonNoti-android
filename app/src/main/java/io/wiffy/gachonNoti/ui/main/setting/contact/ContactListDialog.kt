package io.wiffy.gachonNoti.ui.main.setting.contact

import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.getThemeColor
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.model.adapter.ContactAdapter
import kotlinx.android.synthetic.main.dialog_contact_list.*

class ContactListDialog(context: Context, private val mList: ArrayList<ContactInformation>) :
    SuperContract.SuperDialog(context) {
    lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contact_list)
        contactInformationHi.setBackgroundColor(context.resources.getColor(getThemeColor()))
        adapter = ContactAdapter(mList, context)
        recyclerContact.adapter = adapter
        recyclerContact.layoutManager = LinearLayoutManager(context)

    }
}