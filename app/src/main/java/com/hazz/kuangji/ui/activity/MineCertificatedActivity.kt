package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.charge.*

class MineCertificatedActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_mine_certificated
    }

    override fun initData() {

    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("实名认证")
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setOnLeftIconClickListener { finish() }
    }

    override fun start() {
    }
}