package com.hazz.kuangji.ui.activity.mine

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.*
import kotlinx.android.synthetic.main.activity_mine_exchange_pwd.*


class MineExchangePwdActivity : BaseActivity(), IContractView.LoginView {
    override fun loginSuccess(msg: UserInfo) {

    }

    override fun sendSms(msg: String) {
        mDialog!!.dismiss()
        SToast.showText(getString(R.string.mine_send_success))
        showCountDownView()
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

    override fun registerSucceed(msg: String) {

        SToast.showText(msg)
        finish()
    }


    override fun layoutId(): Int {
        return R.layout.activity_mine_exchange_pwd
    }

    override fun initData() {

    }


    private var mLoginPresenter: LoginPresenter = LoginPresenter(this)
    private var userInfo: UserInfo? = null
    private var countDownTimer: CountDownTimer? = null
    private var mobile=""
    private var username=""
    @SuppressLint("SetTextI18n")
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.find_zijin_pwd))
                .setToolBarBgRescource(R.drawable.bg_blue_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }

        userInfo = SPUtil.getObj("user", UserInfo::class.java)
        if (userInfo != null) {
            et_phone.setText("+86  "+userInfo!!.mobile)
        } else {
            mobile = SPUtil.getString("mobile")
            username= SPUtil.getString("username")
            et_phone.setText("+86 $mobile")
        }


    }

    override fun start() {
        tv_get_code.setOnClickListener {
            if (userInfo != null) {
                mLoginPresenter.sendSMs(userInfo!!.mobile)
            }else{
                mLoginPresenter.sendSMs(mobile)
            }
        }
        tv_bt.setOnClickListener {
            if (TextUtils.isEmpty(et_zijin_pwd.text.toString()) || TextUtils.isEmpty(et_zijin_pwd_again.text.toString())) {
                SToast.showText("请输入资金密码")
                return@setOnClickListener
            }
            if (et_zijin_pwd.text.toString() != et_zijin_pwd_again.text.toString()) {
                SToast.showText("两次输入密码不一致")
                return@setOnClickListener
            }
            if (userInfo != null) {
                mLoginPresenter.forgetPwd(userInfo!!.username, userInfo!!.mobile, et_code.text.toString().trim(),
                        et_zijin_pwd.text.toString().trim(), et_zijin_pwd_again.text.toString().trim(), "2"
                )
            }else{
                mLoginPresenter.forgetPwd(username, mobile, et_code.text.toString().trim(),
                        et_zijin_pwd.text.toString().trim(), et_zijin_pwd_again.text.toString().trim(), "2"
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}
