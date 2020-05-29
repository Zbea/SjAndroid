package com.hazz.kuangji.ui.activity

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_safe.*
import kotlinx.android.synthetic.main.activity_set.mToolBar


class SafeActivity : BaseActivity() {




    override fun layoutId(): Int {
        return R.layout.activity_safe
    }

    override fun initData() {

    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.safe_center))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }



    }
    override fun start() {
        tv_login.setOnClickListener {
            startActivity(Intent(this,ModifyPwdActivity::class.java))

        }

        tv_zijin.setOnClickListener {
            startActivity(Intent(this,FindZijinPwdActivity::class.java))

        }

    }


}
