package com.hazz.kuangji.ui.activity

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.TextUtils
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.mine_activity_forget.*


class ForgetPwdActivity : BaseActivity(), IContractView.LoginView {
    override fun loginSuccess(msg: UserInfo) {

    }

    override fun sendSms(msg: String) {
        mDialog!!.dismiss()
        SToast.showText( getString(R.string.mine_send_success))
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
        return R.layout.mine_activity_forget
    }

    override fun initData() {
    }


    override fun initView() {
    }

    private var mLoginPresenter: LoginPresenter = LoginPresenter(this)
    private var countDownTimer: CountDownTimer? = null
    override fun start() {
        mTvJump.setOnClickListener {
            finish()
        }

        tv_get_code.setOnClickListener {
            if (TextUtils.isEmpty(mEtPhoneOrEmail.text.toString())) {
                SToast.showText(getString(R.string.mine_plz_input_phone))
                return@setOnClickListener
            }
            if (mEtPhoneOrEmail.text.toString().length!=11) {
                SToast.showText(getString(R.string.phone_length_fail))
                return@setOnClickListener
            }
            mLoginPresenter.sendSMs(mEtPhoneOrEmail.text.toString().trim())
        }

        mTvForget.setOnClickListener {
            if (TextUtils.isEmpty(mEtPassword.text.toString())||TextUtils.isEmpty(mEtPasswordConfirm.text.toString())) {
                SToast.showText(getString(R.string.login_pwd_not_null))
                return@setOnClickListener
            }

            if (mEtPassword.text.toString() != mEtPasswordConfirm.text.toString()) {
                SToast.showText(getString(R.string.pwd_not_same))
                return@setOnClickListener
            }
            if(mEtPassword.text.toString().length<6||mEtPasswordConfirm.text.toString().length<6){
                SToast.showText(getString(R.string.pwd_not_length))
                return@setOnClickListener
            }
            mLoginPresenter.forgetPwd(mEtaccount.text.toString(),mEtPhoneOrEmail.text.toString(),
                    mEtCheckCode .text.toString() ,mEtPassword .text.toString(),mEtPasswordConfirm .text.toString(),"1"
                    )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }
}
