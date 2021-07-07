package com.hazz.kuangji.ui.activity.mine

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_download.*
import kotlinx.android.synthetic.main.activity_download.qrCodeView
import kotlinx.android.synthetic.main.activity_download.toolbar
import kotlinx.android.synthetic.main.activity_mine_invitefriends.*

class DownloadActivity : BaseActivity()
{

    private var mCodeBitmap:Bitmap?=null

    override fun layoutId(): Int {
        return R.layout.activity_download
    }

    override fun initData() {

    }

    override fun initView() {

        ToolBarCustom.newBuilder(toolbar as Toolbar)
                .setTitle("下载地址")
                .setOnLeftIconClickListener { finish() }

        tv_address.text=Constants.URL_DOWNLOAD

        val dip2px = Utils.dip2px(this, 160F)
        var createQRCode = QRCodeUtils.createQRCode(Constants.URL_DOWNLOAD, dip2px, dip2px, null)
        qrCodeView.setImageBitmap(createQRCode)


        btnCopy.setOnClickListener {

            val cm = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clipData = ClipData.newPlainText("address",tv_address.text.toString())

            cm.primaryClip = clipData

            SToast.showText("已成功复制下载地址")

        }

    }

    override fun start() {


    }

}