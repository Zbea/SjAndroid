package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_modify_pwd.*


class ModifyPwdActivity : BaseActivity(), LoginContract.LoginView {
    override fun loginSuccess(msg: UserInfo) {

    }

    override fun sendSms(msg: String) {

    }


    override fun registerSucceed(msg: String) {

        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_modify_pwd
    }

    override fun initData() {

    }


    private var mLoginPresenter: LoginPresenter = LoginPresenter(this)

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.modify_pwd))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


    }

    override fun start() {

        tv_bt.setOnClickListener {
            if (et_new_pwd.text.toString() != et_new_pwd_again.text.toString()) {
                SToast.showText(getString(R.string.pwd_not_same))
                return@setOnClickListener
            }
            if(et_new_pwd.text.toString().length<6||et_new_pwd_again.text.toString().length<6){
                SToast.showText(getString(R.string.pwd_not_length))
                return@setOnClickListener
            }
            mLoginPresenter.resetPwd(et_old_pwd.text.toString().trim(), et_new_pwd_again.text.toString().trim())
        }
    }



}
