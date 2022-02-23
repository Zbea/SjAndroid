package com.hazz.kuangji.ui.activity.home


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.bingoogolapple.qrcode.core.QRCodeView
import cn.bingoogolapple.qrcode.zxing.ZXingView
import com.hazz.kuangji.R
import com.hazz.kuangji.utils.FileUtils
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.mine_activity_scan_coin_address.*


class ScanCoinAddressActivity : AppCompatActivity(), QRCodeView.Delegate {

    private lateinit var mToolbar: Toolbar
    private lateinit var mTvRightText: TextView
    private lateinit var mScanView: ZXingView
    private var isOpenLight: Boolean = false
    private val FROM_GALLERY: Int = 10001
    private var params: String? = null

    //收款码生成二维码的开始字段
    private val QR_CODE_START="GAIA-StoreId="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mine_activity_scan_coin_address)
        params = intent.getStringExtra("scan")
        mToolbar = findViewById(R.id.mToolbar)
        mTvRightText = findViewById<TextView>(R.id.mTvRightText)
        mScanView = findViewById(R.id.mScanView)
        mScanView.setDelegate(this)
        ToolBarCustom.newBuilder(mToolbar)
            .setTitle("掃一掃").setTitleColor(resources.getColor( R.color.color_white))
            .setLeftIcon(R.mipmap.icon_back_white)
            .setRightText("相冊").setRightTextColor(resources.getColor( R.color.color_white))
                .setToolBarBgRescource(resources.getColor( R.color.color_translucent))
            .setRightTextIsShow(true)
            .setOnLeftIconClickListener { finish() }

        mTvRightText.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, FROM_GALLERY)
        }

        mImgOpenLight.setOnClickListener {
            if (isOpenLight) {
                isOpenLight = false
                mScanView.closeFlashlight()
            } else {
                isOpenLight = true
                mScanView.openFlashlight()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        mScanView.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        mScanView.stopSpotAndHiddenRect()
        super.onStop()
    }

    override fun onScanQRCodeSuccess(result: String?) {

        if (result.isNullOrEmpty()) {
            mScanView.startSpot()
        } else {
             Log.d("junjun","扫描结果"+result)
            var i =  Intent()
            i.putExtra("result",result )
            setResult(0, i)
            finish()

        }


    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
    }

    override fun onScanQRCodeOpenCameraError() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            val uri = data?.data
            try {
                uri?.let {
                    val file= FileUtils.uri2File(uri,this)
                    file?.let {
                        mScanView.decodeQRCode(file.absolutePath)
                    }

                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
