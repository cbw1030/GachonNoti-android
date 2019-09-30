package io.wiffy.gachonNoti.model.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.isNetworkConnected
import io.wiffy.gachonNoti.model.ParseList
import io.wiffy.gachonNoti.`object`.Component
import io.wiffy.gachonNoti.ui.webView.WebViewActivity
import kotlinx.android.synthetic.main.adapter_notification.view.*
import kotlinx.android.synthetic.main.adapter_notification_dark.view.*

//        TYPE
//        0 -> NOTIFICATION
//        1 -> NEWS
//        2 -> EVENT
//        3 -> SCHOLARSHIP

class NotificationAdapter(
    var items: ParseList,
    private val context: Context,
    private val act: Activity,
    private val type: Int
) : RecyclerView.Adapter<NotificationAdapter.ComponentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ComponentViewHolder(parent)
    override fun getItemCount(): Int = items.size
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ComponentViewHolder, position: Int) {

        items[position].let { item ->
            with(holder) {
                Handler(Looper.getMainLooper()).post {
                    itemView.setBackgroundResource(
                        if (Component.darkTheme) {
                            when {
                                item.isNotification -> R.color.myDarkLight
                                else -> R.color.myDark2
                            }
                        } else {
                            when {
                                item.isNotification -> R.color.notiBackground
                                else -> R.color.WHITE
                            }
                        }
                    )
                    title.text = item.text
                    card.setCardBackgroundColor(
                        ContextCompat.getColorStateList(
                            context, when {
                                item.value.contains("[글로벌]") -> {
                                    contexts.text = "글로벌"
                                    R.color.red
                                }
                                item.value.contains("[메디컬]") -> {
                                    contexts.text = "메디컬"
                                    R.color.green
                                }
                                else -> {
                                    contexts.text = "공통"
                                    R.color.main2DeepBlue
                                }
                            }
                        )
                    )
                    new.visibility =
                        when {
                            item.isNew -> View.VISIBLE
                            else -> View.GONE
                        }
                    save.visibility =
                        when {
                            item.isSave -> View.VISIBLE
                            else -> View.GONE
                        }
                    date.text = item.data

                    itemView.setOnClickListener {
                        if (!Component.surfing) {
                            Component.surfing = true
                            if (isNetworkConnected(act)) {
                                Component.noneVisible = true
                                val myIntent = Intent(act, WebViewActivity::class.java)
                                myIntent.putExtra("bundle", item)
                                act.startActivity(myIntent)
                            } else {
                                Component.surfing = false
                                Toast.makeText(act, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    itemView.setOnLongClickListener {
                        (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(
                            ClipData.newPlainText(item.text, item.link)
                        )
                        Toast.makeText(context, "주소를 복사했습니다.", Toast.LENGTH_SHORT).show()
                        true
                    }
                    card.visibility =
                        when (type) {
                            2 -> View.GONE
                            else -> View.VISIBLE
                        }

                }
            }
        }
    }

    fun update(list: ParseList) {
        items = list
        notificationUpdate()
    }


    private fun notificationUpdate() = try {
        notifyDataSetChanged()
    } catch (e: Exception) {
    }


    inner class ComponentViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            if (Component.darkTheme) {
                R.layout.adapter_notification_dark
            } else {
                R.layout.adapter_notification
            }, parent, false
        )
    ) {

        val title: TextView = if (Component.darkTheme) {
            itemView.titleIn_
        } else {
            itemView.titleIn
        }
        val contexts: TextView = if (Component.darkTheme) {
            itemView.contextsIn_
        } else {
            itemView.contextsIn
        }
        val date: TextView = if (Component.darkTheme) {
            itemView.date_
        } else {
            itemView.date
        }
        val new: ImageView = if (Component.darkTheme) {
            itemView.neww_
        } else {
            itemView.neww
        }
        val save: ImageView = if (Component.darkTheme) {
            itemView.save_
        } else {
            itemView.save
        }
        val card: CardView = if (Component.darkTheme) {
            itemView.contexts_
        } else {
            itemView.contexts
        }
    }

}