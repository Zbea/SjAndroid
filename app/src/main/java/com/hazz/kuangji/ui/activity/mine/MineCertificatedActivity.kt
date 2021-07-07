package com.hazz.kuangji.ui.activity.mine

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_charge.mToolBar
import kotlinx.android.synthetic.main.activity_charge.tv_address
import kotlinx.android.synthetic.main.activity_mine_certificated.*

class MineCertificatedActivity : BaseActivity() {

    private lateinit var mData: Certification

    override fun layoutId(): Int {
        return R.layout.activity_mine_certificated
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("身份信息")
            .setOnLeftIconClickListener { finish() }
    }

    override fun initData() {
        mData = intent.getSerializableExtra("certification") as Certification
        tv_name.text = mData.realName
        tv_mail.text = mData.email
        tv_address.text = mData.address
        tv_number.text = mData.idNum
    }

    override fun start() {
    }
}