package com.hazz.kuangji.ui.activity.mine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_set.*


class SettingActivity : BaseActivity() {

    override fun layoutId(): Int {
        return R.layout.activity_set
    }

    override fun initData() {

    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.setting))
                .setOnLeftIconClickListener { finish() }

        tv_version.text="V"+Utils.getVersionName(this)

    }


    @SuppressLint("WrongConstant")
    override fun start() {
        tv_find_login.setOnClickListener {
            startActivity(Intent(this, ChangePwdActivity::class.java).setFlags(1))
        }
        tv_find_exchange.setOnClickListener {
            
            startActivity(Intent(this, ChangePwdActivity::class.java).setFlags(2))
        }

        rl_version.setOnClickListener {
            Beta.checkAppUpgrade()
        }


    }


}
