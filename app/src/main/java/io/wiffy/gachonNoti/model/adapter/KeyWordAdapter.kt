package io.wiffy.gachonNoti.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.SuperContract
import io.wiffy.gachonNoti.utils.getThemeButtonResource
import io.wiffy.gachonNoti.utils.setSharedItem
import kotlinx.android.synthetic.main.adapter_keyword.view.*

@Suppress("DEPRECATION")
class KeyWordAdapter(
    private var items: MutableList<String>,
    private val context: Context
) :
    RecyclerView.Adapter<KeyWordAdapter.KeyWordViewHolder>(), SuperContract.WiffyObject {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = KeyWordViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: KeyWordViewHolder, position: Int) =
        with(holder) {
            t1.text = items[position]
            bt.setBackgroundResource(getThemeButtonResource())

            bt.setOnClickListener {
                notifyItemRemoved(position)
                items.removeAt(position)
                setSharedItem("notiKeySet", items.toHashSet())
            }
        }


    inner class KeyWordViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_keyword, parent, false)
    ) {
        val t1: TextView = itemView.word
        val bt:Button = itemView.deleteKeyword
    }

}