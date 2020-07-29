package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_mine_safe.*
import kotlinx.android.synthetic.main.activity_set.mToolBar


class MineSafeActivity : BaseActivity() {




    override fun layoutId(): Int {
        return R.layout.activity_mine_safe
    }

    override fun initData() {

    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.safe_center))
                .setToolBarBgRescource(R.drawable.bg_main_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }



    }
    override fun start() {
        tv_login.setOnClickListener {
            startActivity(Intent(this, MineLoginPwdActivity::class.java))

        }

        tv_zijin.setOnClickListener {
            startActivity(Intent(this, MineExchangePwdActivity::class.java))

        }

    }


}
