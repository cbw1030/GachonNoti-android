package io.wiffy.gachonNoti.ui.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.getThemeButtonResource
import io.wiffy.gachonNoti.function.getThemeColor
import io.wiffy.gachonNoti.function.ifNotNullOrElse
import io.wiffy.gachonNoti.function.matrixToBitmap
import io.wiffy.gachonNoti.model.StudentInformation
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class IDCardWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
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

        if (intent?.action == mAction) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisAppWidget = ComponentName(context?.packageName, IDCardWidget::class.java.name)
            val appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget)
            onUpdate(context!!, appWidgetManager, appWidgets)
        }
    }

    companion object {
        const val mAction = "io.wiffy.gachonNoti.rebalgup_widget"

        internal fun updateAppWidget(
            context: Context, appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.widget_idcard)

            context.getSharedPreferences("GACHONNOTICE", Context.MODE_PRIVATE).run {
                changeView(
                    views, StudentInformation(
                        getString("name", null),
                        getString("number", null),
                        getString("id", null),
                        getString("password", null),
                        getString("department", null),
                        getString("image", null),
                        getString("clubCD", null)
                    ), context, appWidgetId, getString("theme", null),
                    getString("name", null)
                )
            }
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
            views.setInt(
                R.id.gachonback_widget,
                "setBackgroundColor",
                context?.resources?.getColor(getThemeColor(theme)) ?: 0
            )
            views.setInt(
                R.id.rebalgup_widget,
                "setBackgroundResource",
                getThemeButtonResource(theme)
            )

            name.ifNotNullOrElse({
                views.setTextViewText(R.id.yourname_widget, info.name)
                views.setTextViewText(R.id.number_widget, info.number)
                views.setTextViewText(R.id.depart_widget, info.department)
                Glide.with(context!!).asBitmap().load(info.imageURL)
                    .into(AppWidgetTarget(context, R.id.imageonyou_widget, views, widgetId))
                qrCode(views, info.number!!, context, widgetId)
            }, {
                views.setTextViewText(R.id.yourname_widget, "박가천")
                views.setTextViewText(R.id.number_widget, "201735829")
                views.setTextViewText(R.id.depart_widget, "어쩌구학과")
                Glide.with(context!!).asBitmap().load(R.drawable.defaultimage)
                    .into(AppWidgetTarget(context, R.id.imageonyou_widget, views, widgetId))
                qrCode(views, "hello", context, widgetId)
            })

            views.setOnClickPendingIntent(
                R.id.rebalgup_widget, PendingIntent.getBroadcast(
                    context, 0, Intent(context, IDCardWidget::class.java).apply {
                        action = mAction
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }

        @SuppressLint("SimpleDateFormat")
        fun qrCode(views: RemoteViews, num: String, context: Context, widgetId: Int) =
            Glide.with(context).asBitmap().load(
                if (num != "hello") {
                    matrixToBitmap(
                        QRCodeWriter().encode(
                            "m$num${SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())}",
                            BarcodeFormat.QR_CODE,
                            400,
                            400
                        )
                    )
                } else {
                    Bitmap.createBitmap(100, 100, Bitmap.Config.RGB_565).apply {
                        for (x in 0 until 100) {
                            for (y in 0 until 100) {
                                setPixel(x, y, Color.WHITE)
                            }
                        }
                    }
                }
            ).into(AppWidgetTarget(context, R.id.qrcode_widget, views, widgetId))
    }
}

