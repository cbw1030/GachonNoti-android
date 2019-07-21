package io.wiffy.gachonNoti.ui.main.notification.news

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
import androidx.recyclerview.widget.RecyclerView
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.data.ParseList
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.ui.webView.WebViewActivity
import kotlinx.android.synthetic.main.adapter.view.*

class NewsAdapter (
    var items: ParseList,
    private val context: Context,
    private val act: Activity
) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = NewsViewHolder(parent)

    override fun getItemCount(): Int = items.size()

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        items.get(position).let { item ->
            with(holder) {
                Handler(Looper.getMainLooper()).post {
                    itemView.setBackgroundResource(R.color.white)
                    title.text = item.text
                    card.visibility = View.GONE
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
                        if (!Util.surfing) {
                            Util.surfing = true
                            if (Util.isNetworkConnected(act)) {
                                Util.novisible = true
                                val myIntent = Intent(act, WebViewActivity::class.java)
                                myIntent.putExtra("bundle", item)
                                act.startActivity(myIntent)
                            } else {
                                Util.surfing = false
                                Toast.makeText(act, "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    itemView.setOnLongClickListener {
                        (context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).primaryClip =
                            ClipData.newPlainText(item.text, item.link)
                        Toast.makeText(context, "주소를 복사했습니다.", Toast.LENGTH_SHORT).show()
                        true
                    }
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

    inner class NewsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter, parent, false)
    ) {
        val title: TextView = itemView.titleIn
        val contexts: TextView = itemView.contextsIn
        val date: TextView = itemView.date
        val new: ImageView = itemView.neww
        val save: ImageView = itemView.save
        val card: CardView = itemView.contexts
    }
}