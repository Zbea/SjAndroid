package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.os.Environment
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_home_contract.*
import kotlinx.android.synthetic.main.activity_home_contract.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class ContractActivity : BaseActivity(){

    private var code=""
    private var isSign="0"
    private var url=""

    override fun layoutId(): Int {
        return R.layout.activity_home_contract
    }

    override fun initData() {
        EventBus.getDefault().register(this)
        StatusBarUtil.darkMode(this,false)
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("合同详情")
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setOnLeftIconClickListener { finish() }

        code=intent.getStringExtra("contract_code")
        isSign=intent.getStringExtra("contract_sign")
        url=Constants.URL_BASE+"contractor?invest_id="+code

        btn_sign.text=if (isSign=="1") "下载合同" else "立即签名"
        btn_sign.setOnClickListener {
            if (isSign=="0")
            {
                startActivity(Intent(this,ContractSigningActivity::class.java).putExtra("contract_code",code))
            }else
            {
                download()
            }
        }
        showPdf()
    }

    override fun start() {
    }

    /**
     * 展示pdf
     */
    private fun showPdf()
    {
        Thread(Runnable {
            try {
                val connection = URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doInput = true
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()
                if (connection.responseCode === 200) {
                    val iss= connection.inputStream
                    pdfView.fromStream(iss).load()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }

    /**
     * 下载pdf
     */
    private fun download()
    {
        mDialog?.show()
        Thread(Runnable {
            try {
                val connection= URL(url).openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.doInput = true
                connection.connectTimeout = 10000
                connection.readTimeout = 10000
                connection.connect()
                if (connection.responseCode=== 200) {
                    val iss= connection.inputStream
                    val arr = ByteArray(1)
                    val baos = ByteArrayOutputStream()
                    val bos = BufferedOutputStream(baos)
                    var n: Int = iss.read(arr)
                    while (n > 0) {
                        bos.write(arr)
                        n = iss.read(arr)
                    }
                    bos.close()
                    var path= Environment.getExternalStorageDirectory().toString() + "/download/$code.pdf"
                    val fos = FileOutputStream(File(path))
                    fos.write(baos.toByteArray())
                    fos.close()
                    connection.disconnect()
                    mDialog?.dismiss()
                    SToast.showText("合同下载成功，请前往文件夹中查看")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event==Constants.CODE_SIGN_BROAD)
        {
//            showPdf()
            isSign="1"
            btn_sign.text=if (isSign=="1") "下载合同" else "立即签名"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
