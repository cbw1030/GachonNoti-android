package io.wiffy.gachonNoti.ui.main.notification

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.adapter.view.*
import java.util.*

class MainAdapter(
    var items: ParseList,
    val context: Context
) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = MainViewHolder(parent)

    override fun getItemCount(): Int = items.size()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

        items.get(position).let { item ->
            with(holder) {
                Handler(Looper.getMainLooper()).post {
                    itemView.setBackgroundResource(
                        when{
                            item.isNoti ->{
                                R.color.notiBackground
                            }else ->{
                                R.color.WHITE
                            }
                        }
                    )
                    title.text = item.text
                    contexts.setTextColor(
                        ContextCompat.getColorStateList(
                            context, when {
                                item.value.contains("[글로벌]") -> {
                                    contexts.text = "[글로벌]"
                                    R.color.red
                                }
                                item.value.contains("[메디컬]") -> {
                                    contexts.text = "[메디컬]"
                                    R.color.green
                                }
                                else -> {
                                    contexts.text = "[공통]"
                                    R.color.mainBlue
                                }
                            }
                        )
                    )
                    new.visibility =
                        when {
                            item.isNew -> {
                                View.VISIBLE
                            }
                            else -> {
                                View.GONE
                            }
                        }
                    save.visibility =
                        when {
                            item.isSave -> {
                                View.VISIBLE
                            }
                            else -> {
                                View.GONE
                            }
                        }
                    date.text = item.data
                }
            }
        }
    }

    fun update(list: ParseList) {
        items = list
        notificationUpdate()
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
        val contexts: TextView = itemView.contexts
        val date: TextView = itemView.date
        val new: ImageView = itemView.neww
        val save: ImageView = itemView.save
    }

}