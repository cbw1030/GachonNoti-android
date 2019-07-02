package io.wiffy.gachonNoti.ui.main.setting

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import kotlinx.android.synthetic.main.adapter_contact.view.*

class ContactAdapter(
    var items: ArrayList<ContactInformation>
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ContactViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                t1.text = item.dept
                t2.text = item.name
                t3.text = item.tel
            }
        }
    }


    inner class ContactViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_contact, parent, false)
    ) {
        val t1: TextView = itemView.AAA2
        val t2: TextView = itemView.BBB2
        val t3: TextView = itemView.CCC2
    }

}