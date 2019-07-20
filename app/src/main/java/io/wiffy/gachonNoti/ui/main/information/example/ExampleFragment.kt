package io.wiffy.gachonNoti.ui.main.information.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.model.*
import kotlinx.android.synthetic.main.fragment_information_example.view.*
import java.text.SimpleDateFormat


class ExampleFragment : Fragment(), ExampleContract.View {
    private val initiation = 300000
    private val initiationText = "05 분 00 초"
    var myView: View? = null
    lateinit var mPresenter: ExamplePresenter
    private var mInfo: StudentInformation? = null
    lateinit var handler: Handler
    var count = initiation
    private var handlerTask: Runnable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_example, container, false)
        handler = Handler()
        mPresenter = ExamplePresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun initView() {
        changeTheme()
        if (mInfo != null) loginInformationSetting(mInfo!!)
    }

    fun changeTheme() {
        myView?.gachonback?.setBackgroundColor(
            when (Util.theme) {
                "red" -> ContextCompat.getColor(context!!, R.color.red)
                "green" -> ContextCompat.getColor(context!!, R.color.green)
                else -> ContextCompat.getColor(context!!, R.color.main2Blue)
            }
        )
        myView?.rebalgup?.setBackgroundResource(
            when (Util.theme) {
                "red" -> R.drawable.dialog_button_red
                "green" -> R.drawable.dialog_button_green
                else -> R.drawable.dialog_button_default
            }
        )
    }

    fun loginInformationSetting(info: StudentInformation) {
        mInfo = info
        if (myView != null)
            with(info)
            {
                myView?.yourname?.text = name
                myView?.number?.text = number
                myView?.depart?.text = department

                Glide.with(activity!!)
                    .load(imageURL)
                    .override(myView?.imageonyou?.width!!, myView?.imageonyou?.height!!)
                    .into(myView?.imageonyou!!)

                setTimerAndQRCode(number)
                myView?.rebalgup?.setOnClickListener {
                    setTimerAndQRCode(number)
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    fun setTimerAndQRCode(number: String) {
        if (handlerTask != null) handler.removeCallbacks(handlerTask)
        myView?.timer?.text = initiationText
        count = initiation
        handlerTask = object : Runnable {
            @SuppressLint("SetTextI18n")
            override fun run() {
                count -= 1000
                val m = (count / 1000) / 60
                val s = (count / 1000) % 60

                myView?.timer?.text = "0$m 분 ${when {
                    s < 10 -> "0$s"
                    else -> "$s"
                }
                } 초"
                if (count > 0) handler.postDelayed(this, 1000)
                else handler.removeCallbacks(this)

            }
        }

        handler.post(handlerTask)

        val format = SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis())
        val output = StringBuilder("m$number$format")
        output.append(Integer.parseInt("0A", 16))
        val qrCodeWriter = QRCodeWriter()
        val bitmap =
            Util.matrixToBitmap(qrCodeWriter.encode(output.toString(), BarcodeFormat.QR_CODE, 200, 200))
        myView?.qrcode?.setImageBitmap(bitmap)
    }

}