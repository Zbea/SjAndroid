package com.hazz.kuangji.ui.activity.mine

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_mine_exchange_pwd.*
import kotlinx.android.synthetic.main.activity_mine_exchange_pwd.tv_get_code


class ChangePwdActivity : BaseActivity(), IContractView.LoginView {

    private var mLoginPresenter: LoginPresenter = LoginPresenter(this)
    private var countDownTimer: CountDownTimer? = null
    private var mobile = ""
    private var username = ""
    private var type=1


    override fun loginSuccess(msg: UserInfo) {
    }

    override fun sendSms(msg: String) {
        mDialog!!.dismiss()
        SToast.showText(getString(R.string.mine_send_success))
        showCountDownView()
    }

    override fun registerSucceed(msg: String) {

        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_mine_exchange_pwd
    }

    override fun initData() {

    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        var toolbar=ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setOnLeftIconClickListener { view -> finish() }

        type=intent.flags

        if (type==1){
            toolbar.setTitle("修改登录密码")
        }
        else if (type==2){
            toolbar.setTitle("修改资金密码")
        }
        else{
            toolbar.setTitle("忘记密码")
            type=1
            et_username.visibility= View.VISIBLE
        }

        mobile = SPUtil.getString("mobile")
        username = SPUtil.getString("username")
        et_phone.setText("$mobile")
        et_username.setText(username)
    }

    override fun start() {
        tv_get_code.setOnClickListener {
            if (TextUtils.isEmpty(et_phone.text.toString())) {
                SToast.showText(getString(R.string.mine_plz_input_phone))
                return@setOnClickListener
            }
            if (et_phone.text.toString().length != 11) {
                SToast.showText(getString(R.string.phone_length_fail))
                return@setOnClickListener
            }
            mLoginPresenter.sendSMs(et_phone.text.toString().trim(), type.toString())
        }
        tv_bt.setOnClickListener {
            username=et_username.text.toString()
            if (username.isNullOrEmpty())
            {
                SToast.showText("请输入用户名")
                return@setOnClickListener
            }
            mobile=et_phone.text.toString()
            if (mobile.isNullOrEmpty())
            {
                SToast.showText("请输入手机号")
                return@setOnClickListener
            }
            var code=et_code.text.toString()
            if (code.isNullOrEmpty())
            {
                SToast.showText("请输入验证码")
                return@setOnClickListener
            }
            var pwd=et_pwd.text.toString()
            if (pwd.isNullOrEmpty())
            {
                SToast.showText("请输入密码")
                return@setOnClickListener
            }
            var pwdAgain=et_pwd_again.text.toString()
            if (pwdAgain.isNullOrEmpty())
            {
                SToast.showText("请再次输入密码")
                return@setOnClickListener
            }
            if (pwd != pwdAgain) {
                SToast.showText("两次输入密码不一致")
                return@setOnClickListener
            }
            mLoginPresenter.forgetPwd(username, mobile, code, pwd, type)
        }
    }

    private fun showCountDownView() {
        tv_get_code.isEnabled = false
        tv_get_code.isClickable = false
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tv_get_code.isEnabled = true
                tv_get_code.isClickable = true
                tv_get_code.text = getString(R.string.mine_get_check_code)
            }

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                tv_get_code.text = "${millisUntilFinished / 1000}s"
            }
        }.start()

    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}
