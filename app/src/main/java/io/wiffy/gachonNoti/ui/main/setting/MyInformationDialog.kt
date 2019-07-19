package io.wiffy.gachonNoti.ui.main.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.SslConnect
import io.wiffy.gachonNoti.model.StudentInformation
import io.wiffy.gachonNoti.model.Util
import kotlinx.android.synthetic.main.dialog_myinfo.*
import java.text.SimpleDateFormat

class MyInformationDialog(context: Context) : Dialog(context) {
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_myinfo)
        if (!Util.isLogin) {
            info_on.visibility = View.GONE
            info_off.visibility = View.VISIBLE
        } else {
            info_on.visibility = View.VISIBLE
            info_off.visibility = View.GONE
            val x = with(Util.sharedPreferences)
            {
                StudentInformation(
                    getString("name", "null") ?: "null",
                    getString("number", "null") ?: "null",
                    getString("id", "null") ?: "null",
                    getString("password", "null") ?: "null",
                    getString("department", "null") ?: "null",
                    getString("image", "null") ?: "null"
                )
            }
            Toast.makeText(context, x.imageURL, Toast.LENGTH_SHORT).show()
            Log.d("asdf", x.imageURL)
            with(x) {
                Glide.with(context).load(imageURL).into(yourphoto)
                val format = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
                val output = StringBuilder("m$number$format")
                output.append(Integer.parseInt("0A", 16))
                val qrCodeWriter = QRCodeWriter()
                val bitmap =
                    Util.matrixToBitmap(qrCodeWriter.encode(output.toString(), BarcodeFormat.QR_CODE, 200, 200))
                myText.text = "성명 : $name \n 학번 : $number"
                yourQRcode.setImageBitmap(bitmap)
            }

        }
    }
}