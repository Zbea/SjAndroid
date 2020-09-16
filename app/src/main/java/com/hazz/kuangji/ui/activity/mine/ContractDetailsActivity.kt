package com.hazz.kuangji.ui.activity.mine

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_contract_details.*
import kotlinx.android.synthetic.main.activity_contract_details.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

/**
 * @Created by Zbea
 * @fileName ContractManagerActivity
 * @date 2020/8/18 14:33
 * @email xiaofeng9212@126.com
 * @describe 合同详情
 **/
class ContractDetailsActivity : BaseActivity(){

    private var code=""
    private var isSign="0"
    private var url=""
    private var file:File?=null

    override fun layoutId(): Int {
        return R.layout.activity_contract_details
    }

    override fun initData() {
        EventBus.getDefault().register(this)
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("合同详情")
                .setOnLeftIconClickListener { finish() }

        code=intent.getStringExtra("contract_code")
        isSign=intent.getStringExtra("contract_sign")
        url=Constants.URL_BASE+"contractor?invest_id="+code
        file=File(Environment.getExternalStorageDirectory().toString() ,"/eye/M000000$code.pdf")
        btn_sign.visibility=if (isSign=="2")View.GONE else View.VISIBLE
        btn_sign.text=if (isSign=="1") "下载及分享" else "立即签名"
        btn_sign.setOnClickListener {
            if (isSign=="0")
            {
                startActivity(Intent(this,ContractSigningActivity::class.java).putExtra("contract_code",code))
            }else
            {
                permissionsnew?.request(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )?.subscribe { permission ->
                    if (permission!!) {
                        download()
                    } else {
                        showMissingPermissionDialog()
                    }
                }
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
                    pdfView?.fromStream(iss)?.load()
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
                if (connection.responseCode== 200) {
                    val iss= connection.inputStream
                    val arr = ByteArray(1)
                    val baos = ByteArrayOutputStream ()
                    val bos = BufferedOutputStream(baos)
                    var n: Int = iss.read(arr)
                    while (n > 0) {
                        bos.write(arr)
                        n = iss.read(arr)
                    }
                    val galleryPath = Environment.getExternalStorageDirectory().toString() + "/eye/"
                    val dir = File(galleryPath)
                    if (!dir.exists()) {
                        dir.mkdirs()
                    }
                    bos.close()
                    val fos = FileOutputStream(file)
                    fos.write(baos.toByteArray())
                    fos.close()
                    connection.disconnect()
                    mDialog?.dismiss()
//                    SToast.showText("合同下载成功，请前往文件管理眼球矿机查看")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()

        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, file?.let { FileProvider.getUriForFile(this, applicationContext.packageName + ".provider", it) })
        intent.type = "*/*"
        startActivity(intent)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(data: Contract) {
        if (data!=null)
        {
            url=Constants.URL_INVITE+data.path
            showPdf()
            isSign="1"
            btn_sign.text=if (isSign=="1") "下载及分享" else "立即签名"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }



}
