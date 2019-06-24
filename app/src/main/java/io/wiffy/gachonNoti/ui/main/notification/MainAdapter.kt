package io.wiffy.gachonNoti.ui.main.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import kotlinx.android.synthetic.main.adapter.view.*

class MainAdapter(
    var items:ArrayList<String>
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                title.text = item
            }
        }
    }


    private fun notificationUpdate() {
        try {
            notifyDataSetChanged()
        } catch (e: Exception) {
        }
    }

    inner class MainViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter, parent, false)
    ) {
        val title: TextView = itemView.titleIn
    }

}