package com.hazz.kuangji.ui.activity.mine

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ImageUtlis
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar

class MineContactActivity : BaseActivity()
{

    private var mCodeBitmap:Bitmap?=null
    private var type=0

    override fun layoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initData() {

    }

    override fun initView() {

        type=intent.flags

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(if (type==0)"联系我们" else "下载地址")
                .setOnLeftIconClickListener { finish() }

        qrCodeView.setImageResource(if (type==0)R.mipmap.icon_gz_qrcode else R.mipmap.icon_mine_download_qrcode)

    }

    override fun start() {
        mCodeBitmap=BitmapFactory.decodeResource(resources,if (type==0)R.mipmap.icon_gz_qrcode else R.mipmap.icon_mine_download_qrcode)
        tv_down.setOnClickListener {
            permissionsnew!!.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE

            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("图片保存成功")

                    ImageUtlis.saveBmp2Gallery(this,mCodeBitmap,"gzcode")

                } else {
                    showMissingPermissionDialog()
                }
            }
        }

    }

}