package io.wiffy.gachonNoti.ui.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.widget.Button
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.Util
import io.wiffy.gachonNoti.model.data.StudentInformation
import java.text.SimpleDateFormat

/**
 * Implementation of App Widget functionality.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Widget : AppWidgetProvider() {


    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        val mAction = intent?.action
        if (mAction == action) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisAppWidget = ComponentName(context?.packageName, Widget::class.java.name)
            val appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget)
            onUpdate(context!!, appWidgetManager, appWidgets)
        }
    }

    companion object {
        const val action = "io.wiffy.gachonNoti.rebalgup_widget"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_main)

            val pref = context.getSharedPreferences("GACHONNOTICE", Context.MODE_PRIVATE)

            changeView(
                views, StudentInformation(
                    pref.getString("name", null),
                    pref.getString("number", null),
                    pref.getString("id", null),
                    pref.getString("password", null),
                    pref.getString("department", null),
                    pref.getString("image", null)
                ), context, appWidgetId, pref.getString("theme", null),
                pref.getString("name", null)
            )

            // Instruct the widget_main manager to update the widget_main
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun changeView(
            views: RemoteViews,
            info: StudentInformation,
            context: Context?,
            widgetId: Int,
            theme: String?,
            name: String?
        ) {
            val color = context?.resources?.getColor(
                when (theme) {
                    "red" -> {
                        R.color.red
                    }
                    "green" -> {
                        R.color.green
                    }
                    else -> {
                        R.color.main2Blue
                    }
                }
            ) ?: 0

            val buttonColor =
                when (theme) {
                    "red" -> {
                        R.drawable.dialog_button_red
                    }
                    "green" -> {
                        R.drawable.dialog_button_green
                    }
                    else -> {
                        R.drawable.dialog_button_default
                    }
                }

            views.setInt(R.id.gachonback_widget, "setBackgroundColor", color)
            views.setInt(R.id.rebalgup_widget, "setBackgroundResource", buttonColor)
            if (name != null) {
                views.setTextViewText(R.id.yourname_widget, info.name)
                views.setTextViewText(R.id.number_widget, info.number)
                views.setTextViewText(R.id.depart_widget, info.department)
                Glide.with(context!!).asBitmap().load(info.imageURL)
                    .into(AppWidgetTarget(context, R.id.imageonyou_widget, views, widgetId))
                qrCode(views, info.number!!, context, widgetId)
            } else {
                views.setTextViewText(R.id.yourname_widget, "박가천")
                views.setTextViewText(R.id.number_widget, "201735829")
                views.setTextViewText(R.id.depart_widget, "어쩌구학과")
                Glide.with(context!!).asBitmap().load(R.drawable.defaultimage)
                    .into(AppWidgetTarget(context, R.id.imageonyou_widget, views, widgetId))
                Glide.with(context).asBitmap().load(R.color.white)
                    .into(AppWidgetTarget(context, R.id.qrcode_widget, views, widgetId))
            }

            val myIntent = Intent(context, Widget::class.java)
            myIntent.action = action

            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, myIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setOnClickPendingIntent(R.id.rebalgup_widget, pendingIntent)
        }

        @SuppressLint("SimpleDateFormat")
        fun qrCode(views: RemoteViews, num: String, context: Context, widgetId: Int) {
            val format = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
            val output = "m$num$format"
            val qrCodeWriter = QRCodeWriter()
            val bitmap =
                Util.matrixToBitmap(qrCodeWriter.encode(output, BarcodeFormat.QR_CODE, 400, 400))
            Glide.with(context).asBitmap().load(bitmap)
                .into(AppWidgetTarget(context, R.id.qrcode_widget, views, widgetId))
        }
    }
}

