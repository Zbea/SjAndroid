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

    override fun layoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initData() {

    }

    override fun initView() {

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("联系我们" )
                .setRightText("保存")
                .setRightTextColor(resources.getColor(R.color.color_main))
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { finish() }
                .setOnRightClickListener {
                    permissionsnew!!.request(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ).subscribe { permission ->
                        if (permission!!) {
                            SToast.showText("图片保存成功")
                            ImageUtlis.saveBmpGallery(this,mCodeBitmap,"contactCode")
                        } else {
                            showMissingPermissionDialog()
                        }
                    }
                }
        qrCodeView.setImageResource(R.mipmap.icon_gz_qrcode )

    }

    override fun start() {
        mCodeBitmap=BitmapFactory.decodeResource(resources,R.mipmap.icon_gz_qrcode)
    }

}