package com.hazz.kuangji.ui.activity.mine

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.ui.activity.LoginActivity
import com.hazz.kuangji.utils.ActivityManager
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.CommonDialog
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_set.*
import kotlinx.android.synthetic.main.activity_set.tv_login
import kotlinx.android.synthetic.main.activity_set.tv_zijin
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
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        tv_version.text=Utils.getVersionName(this)

    }


    override fun start() {
        tv_login.setOnClickListener {
            startActivity(Intent(this, MineLoginPwdActivity::class.java))
        }
        tv_zijin.setOnClickListener {
            startActivity(Intent(this, MineExchangePwdActivity::class.java))
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
        SPUtil.removeObj("certification")
        startActivity(Intent(this, LoginActivity::class.java))
        ActivityManager.getInstance().finishOthers(LoginActivity::class.java)
    }

}
