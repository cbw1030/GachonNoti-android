@file:Suppress("DEPRECATION")

package io.wiffy.gachonNoti.ui.widget

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.widget.RemoteViews
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.AppWidgetTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.palecosmos.escapableforeach.escapableForEach
import com.skydoves.whatif.whatIfNotNull
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.function.*
import io.wiffy.gachonNoti.model.StudentInformation
import java.text.SimpleDateFormat

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "DEPRECATION")
class IDCardWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.escapableForEach { _, value ->
            updateAppWidget(context, appWidgetManager, value)
            true
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
            val thisAppWidget = ComponentName(context?.packageName!!, IDCardWidget::class.java.name)
            val appWidgets = appWidgetManager.getAppWidgetIds(thisAppWidget)
            onUpdate(context, appWidgetManager, appWidgets)
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
                    ), context, appWidgetId,
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
            name: String?
        ) {

            views.setInt(
                R.id.gachonback_widget,
                "setBackgroundResource",
                getWidgetLayoutColor()
            )
            views.setInt(
                R.id.rebalgup_widget,
                "setBackgroundResource",
                getThemeButtonResource()
            )

            name.whatIfNotNull(
                whatIf = {
                    views.setTextViewText(R.id.yourname_widget, info.name)
                    views.setTextViewText(R.id.number_widget, info.number)
                    views.setTextViewText(R.id.depart_widget, info.department)
                    Glide.with(context!!).asBitmap().centerCrop().load(info.imageURL)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                val bitmap = getRoundedCornerBitmap(resource, 5)
                                Glide.with(context).asBitmap().load(bitmap)
                                    .into(
                                        AppWidgetTarget(
                                            context,
                                            R.id.imageonyou_widget,
                                            views,
                                            widgetId
                                        )
                                    )
                            }
                        })
                    qrCode(views, info.number!!, context, widgetId)
                },
                whatIfNot = {
                    views.setTextViewText(R.id.yourname_widget, "박가천")
                    views.setTextViewText(R.id.number_widget, "201735829")
                    views.setTextViewText(R.id.depart_widget, "어쩌구학과")
                    Glide.with(context!!).asBitmap().load(R.drawable.defaultimage)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                val bitmap = getRoundedCornerBitmap(resource)
                                Glide.with(context).asBitmap().load(bitmap)
                                    .into(
                                        AppWidgetTarget(
                                            context,
                                            R.id.imageonyou_widget,
                                            views,
                                            widgetId
                                        )
                                    )
                            }
                        })
                    qrCode(views, "hello", context, widgetId)
                }
            )

            views.setOnClickPendingIntent(
                R.id.rebalgup_widget, PendingIntent.getBroadcast(
                    context, 0, Intent(context, IDCardWidget::class.java).apply {
                        action = mAction
                    },
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )
        }

        private fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int = 10): Bitmap {
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap
                    .height, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)

            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = pixels.toFloat()

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = -0xbdbdbe
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)

            return output
        }

        @SuppressLint("SimpleDateFormat")
        private fun qrCode(views: RemoteViews, num: String, context: Context, widgetId: Int) =
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

