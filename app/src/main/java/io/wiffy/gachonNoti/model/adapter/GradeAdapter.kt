package io.wiffy.gachonNoti.model.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.CreditFormal
import kotlinx.android.synthetic.main.adapter_credit.view.*

class GradeAdapter(
    private var items: ArrayList<CreditFormal>
) : RecyclerView.Adapter<GradeAdapter.GradeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = GradeViewHolder(parent)
    override fun getItemCount(): Int = items.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GradeViewHolder, position: Int) {
        items[position].let { item ->
            with(holder) {
                year.text = "${item.year}년\n${item.term}"
                name.text = item.name
                grade.text = "${item.grade}"
                credit.text = "${item.credit}"
                mark.text = item.mark
            }
        }
    }

    fun update(list: ArrayList<CreditFormal>) {
        items = list
        notificationUpdate()
    }


    private fun notificationUpdate() = try {
        notifyDataSetChanged()
    } catch (e: Exception) {
    }


    inner class GradeViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_credit, parent, false)
    ) {
        val year: TextView = itemView.creditYear2
        val name: TextView = itemView.creditName2
        val grade: TextView = itemView.creditGrade2
        val credit: TextView = itemView.creditCredit2
        val mark: TextView = itemView.creditMark2
    }
}