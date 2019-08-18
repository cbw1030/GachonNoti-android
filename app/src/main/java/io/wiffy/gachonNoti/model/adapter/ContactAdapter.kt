package io.wiffy.gachonNoti.model.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.model.Component
import kotlinx.android.synthetic.main.adapter_contact.view.*

class ContactAdapter(
    var items: ArrayList<ContactInformation>,
    val context: Context
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ContactViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) = items[position].let { item ->
        with(holder) {
            t1.text = item.dept
            t2.text = item.name
            t3.text = Html.fromHtml(
                "<u>${item.tel}</u>"
            )
            t3.setTextColor(
                context.resources.getColorStateList(
                    when (Component.theme) {
                        "red" -> R.color.red
                        "green" -> R.color.green
                        else -> R.color.main2Blue
                    }
                )
            )

            t3.setOnClickListener {
                val code = item.tel.replace("-", "")
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$code")))
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