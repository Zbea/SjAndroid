package com.hazz.kuangji.ui.activity

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ActivityManager
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.TipsDialog
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.invitefriends_record.mToolBar


class SettingActivity : BaseActivity() {




    override fun layoutId(): Int {
        return R.layout.activity_set
    }

    override fun initData() {

    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.setting))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        tv_version.text=Utils.getVersionName(this)

    }
    override fun start() {
        safe.setOnClickListener {
            startActivity(Intent(this,SafeActivity::class.java))
        }
        tv_logout.setOnClickListener {


              val tipsDialog = TipsDialog(this)
                      .setTitle("提示")
                      .setContent("确认要退出登录吗？")
                      .setConfirmText("确认")
                      .setCancleText("取消")
                      .setCancleListener { }
                      .setConfirmListener {


                          SPUtil.putString("token", "")
                          startActivity(Intent(this, LoginActivity::class.java))
                          ActivityManager.getInstance().finishOthers(LoginActivity::class.java)                     }

            tipsDialog.show()
        }

        rl_version.setOnClickListener {
            Beta.checkAppUpgrade()
        }
    }


}
