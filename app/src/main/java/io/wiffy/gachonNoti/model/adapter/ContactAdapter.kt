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
import io.wiffy.gachonNoti.utils.getThemeColor
import io.wiffy.gachonNoti.model.ContactInformation
import io.wiffy.gachonNoti.model.SuperContract
import kotlinx.android.synthetic.main.adapter_contact.view.*

@Suppress("DEPRECATION")
class ContactAdapter(
    private var items: ArrayList<ContactInformation>,
    private val context: Context
) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>(), SuperContract.WiffyObject {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ContactViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        items[position].let { item ->
            with(holder) {
                t1.text = item.dept
                t2.text = item.name
                t3.text = Html.fromHtml(
                    "<u>${item.tel}</u>"
                )
                t3.setTextColor(
                    context.resources.getColorStateList(getThemeColor())
                )

                t3.setOnClickListener {
                    context.startActivity(
                        Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${item.tel.replace("-", "")}")
                        )
                    )
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