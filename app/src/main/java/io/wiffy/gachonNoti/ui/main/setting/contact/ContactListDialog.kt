package io.wiffy.gachonNoti.ui.main.setting.contact

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.adapter.ContactAdapter
import io.wiffy.gachonNoti.model.data.ContactInformation
import kotlinx.android.synthetic.main.dialog_contact_list.*

class ContactListDialog(context: Context, val mList: ArrayList<ContactInformation>) : Dialog(context) {
    lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_contact_list)
        contactInformationHi.setBackgroundColor(context.resources.getColor(when(Util.theme){
            "red"->R.color.red
            "green"->R.color.green
            else->R.color.main2Blue
        }))
        adapter = ContactAdapter(mList, context)
        recyclerContact.adapter = adapter
        recyclerContact.layoutManager = LinearLayoutManager(context)

    }
}