package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_mine_certificated.*
import kotlinx.android.synthetic.main.activity_mine_certificated.tv_name
import kotlinx.android.synthetic.main.charge.*
import kotlinx.android.synthetic.main.charge.mToolBar
import kotlinx.android.synthetic.main.charge.tv_address
import kotlinx.android.synthetic.main.item_node.*

class MineCertificatedActivity : BaseActivity() {

    private lateinit var mData:Certification

    override fun layoutId(): Int {
        return R.layout.activity_mine_certificated
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("实名认证")
                .setTitleColor(resources.getColor(R.color.color_white))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setOnLeftIconClickListener { finish() }
    }

    override fun initData() {
        mData=intent.getSerializableExtra("certification") as Certification
        tv_username.text= SPUtil.getString("username")
        tv_name.text=mData.name
        tv_mail.text=mData.email
        tv_address.text=mData.address
        tv_number.text=mData.idNumber
        iv_status.setImageResource(if (mData.status==0) R.mipmap.icon_mine_certificating else R.mipmap.icon_mine_certificated)
    }

    override fun start() {
    }
}