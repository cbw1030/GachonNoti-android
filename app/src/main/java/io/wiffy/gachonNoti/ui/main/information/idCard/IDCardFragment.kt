package io.wiffy.gachonNoti.ui.main.information.idCard

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import io.wiffy.gachonNoti.R
import io.wiffy.gachonNoti.func.getThemeButtonResource
import io.wiffy.gachonNoti.func.getThemeColor
import io.wiffy.gachonNoti.func.matrixToBitmap
import io.wiffy.gachonNoti.model.StudentInformation
import kotlinx.android.synthetic.main.fragment_information_idcard.view.*
import java.text.SimpleDateFormat

class IDCardFragment : IDCardContract.View() {
    private val initiation = 300000
    private val initiationText = "05 분 00 초"
    var myView: View? = null
    lateinit var mPresenter: IDCardPresenter
    private var mInfo: StudentInformation? = null
    lateinit var handler: Handler
    var count = initiation
    private var handlerTask: Runnable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        myView = inflater.inflate(R.layout.fragment_information_idcard, container, false)
        handler = Handler()
        mPresenter = IDCardPresenter(this)
        mPresenter.initPresent()
        return myView
    }

    override fun initView() {
        changeTheme()
        if (mInfo != null) loginInformationSetting(mInfo!!)
    }

    fun changeTheme() {
        myView?.gachonback?.setBackgroundColor(ContextCompat.getColor(context!!, getThemeColor()))
        myView?.rebalgup?.setBackgroundResource(getThemeButtonResource())
    }

    fun loginInformationSetting(info: StudentInformation) {
        mInfo = info
        myView?.let {
            with(info)
            {
                myView?.yourname?.text = name
                myView?.number?.text = number
                myView?.depart?.text = department

                Glide.with(activity!!)
                    .load(imageURL)
                    .into(myView?.imageonyou!!)

                setTimerAndQRCode(number!!)
                myView?.rebalgup?.setOnClickListener {
                    setTimerAndQRCode(number)
                }
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
        val output = "m$number$format"
        val qrCodeWriter = QRCodeWriter()
        val bitmap =
            matrixToBitmap(qrCodeWriter.encode(output, BarcodeFormat.QR_CODE, 400, 400))

        Glide.with(activity!!)
            .load(bitmap)
            .into(myView?.qrcode!!)

    }

}