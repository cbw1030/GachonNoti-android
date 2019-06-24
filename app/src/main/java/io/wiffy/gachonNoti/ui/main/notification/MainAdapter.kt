package io.wiffy.gachonNoti.ui.main.notification

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
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
        val config = context.resources.configuration
        config.locale = Locale(Util.global)
        val res = Resources(context.assets, context.resources.displayMetrics, config)

        items.get(position).let { item ->
            with(holder) {
                Handler(Looper.getMainLooper()).post {
                    title.text = item.text
                    contexts.setTextColor(
                        ContextCompat.getColorStateList(
                            context, when {
                                item.value.contains("[글로벌]") -> {
                                    contexts.text = "[${res.getString(R.string.global)}]"
                                    R.color.red
                                }
                                item.value.contains("[메디컬]") -> {
                                    contexts.text = "[${res.getString(R.string.medical)}]"
                                    R.color.green
                                }
                                else -> {
                                    contexts.text ="[${res.getString(R.string.common)}]"
                                    R.color.mainBlue
                                }
                            }
                        )
                    )
                    date.text = item.data
                }
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
        val contexts: TextView = itemView.contexts
        val date: TextView = itemView.date
    }

}