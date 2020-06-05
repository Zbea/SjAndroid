package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.TextUtils
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.mine_activity_login.*


class LoginActivity : BaseActivity(), IContractView.LoginView {
    override fun sendSms(msg: String) {

    }

    override fun registerSucceed(msg: String) {
    }


    override fun loginSuccess(msg: UserInfo) {
        SPUtil.putObj("user",msg)
        SPUtil.putString("mobile",msg.mobile)
        SPUtil.putString("username",msg.username)
        SPUtil.putString("invitation_code",msg.invitation_code)
        SPUtil.putString("invitation_code_url",msg.invitation_code_url)
        SPUtil.putString("token",msg.token)
        SToast.showText(getString(R.string.login_succeed))
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


    private var mLoginPresenter:LoginPresenter= LoginPresenter(this)
    override fun layoutId(): Int {
        return R.layout.mine_activity_login
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun start() {
        mTvLogin.setOnClickListener {
            if(TextUtils.isEmpty(mEtPhoneOrEmail.text.toString())){
              SToast.showText("请输入用户名")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(mEtPassword.text.toString())){
                SToast.showText(getString(R.string.mine_plz_input_password))
                return@setOnClickListener
            }

            mLoginPresenter.login(mEtPhoneOrEmail.text.toString().trim(),mEtPassword.text.toString().trim())
           //
        }

        mTvRegist.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        mTvUseForgetPassword.setOnClickListener {
            startActivity(Intent(this, ForgetPwdActivity::class.java))
        }
    }


}
