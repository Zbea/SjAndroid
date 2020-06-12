package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.os.Handler
import android.os.Message
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.utils.ActivityManager
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.CommonDialog
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.invitefriends_record.mToolBar


class SettingActivity : BaseActivity() {


    var commonDialog: CommonDialog? =null;

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
            commonDialog=CommonDialog(this)
            commonDialog?.run {
                setContent("确认要退出登录吗？")
                setDialogClickListener(object : CommonDialog.DialogClickListener
                {
                    override fun ok() {
                        outLogin()
                    }
                    override fun cancel() {
                    }
                })
                builder()
                show()
            }
        }

        rl_version.setOnClickListener {
            Beta.checkAppUpgrade()
        }
    }


    fun outLogin()
    {
        SPUtil.putString("token", "")
        startActivity(Intent(this, LoginActivity::class.java))
        ActivityManager.getInstance().finishOthers(LoginActivity::class.java)
    }

}
