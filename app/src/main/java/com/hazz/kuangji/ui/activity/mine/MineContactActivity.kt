package com.hazz.kuangji.ui.activity.mine

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ImageUtils
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_contact.*

class MineContactActivity : BaseActivity() {

    private var mCodeBitmap: Bitmap? = null

    override fun layoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initData() {

    }

    override fun initView() {

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("聯系我們")
            .setOnLeftIconClickListener { finish() }

    }

    override fun start() {
        mCodeBitmap = BitmapFactory.decodeResource(resources, R.mipmap.icon_gz_qrcode_address)
        btn_save.setOnClickListener {
            permissionsnew!!.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe { permission ->
                if (permission!!) {

                    SToast.showText("圖片保存成功")
                    ImageUtils.saveBmp2Gallery(this, mCodeBitmap, "contactCode")
                } else {
                    showMissingPermissionDialog()
                }
            }
        }
    }

}