package io.wiffy.gachonNoti.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.CreditInformation
import kotlinx.android.synthetic.main.adapter_credit_information.view.*

class CreditAdapter(
    var items: ArrayList<CreditInformation>
) : RecyclerView.Adapter<CreditAdapter.CreditViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = CreditViewHolder(parent)
    override fun getItemCount(): Int = items.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CreditViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                a1.text = item.name
                a2.text = item.isu
                a3.text = item.chu
            }
        }
    }

    fun update(list: ArrayList<CreditInformation>) {
        items = list
        notificationUpdate()
    }


    private fun notificationUpdate() = try {
        notifyDataSetChanged()
    } catch (e: Exception) {
    }


    inner class CreditViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_credit_information, parent, false)
    ) {
        val a1: TextView = itemView.isu_
        val a2: TextView = itemView.isu2_
        val a3: TextView = itemView.creditGrade_
    }
}