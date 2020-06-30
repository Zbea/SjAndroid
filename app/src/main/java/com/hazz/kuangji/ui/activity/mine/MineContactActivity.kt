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
import kotlinx.android.synthetic.main.activity_rule.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar

class MineContactActivity : BaseActivity()
{

    private var mCodeBitmap:Bitmap?=null;

    override fun layoutId(): Int {
        return R.layout.activity_contact
    }

    override fun initData() {

    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("联系我们")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }
    }

    override fun start() {
        mCodeBitmap=BitmapFactory.decodeResource(resources,R.mipmap.icon_gz_qrcode)
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