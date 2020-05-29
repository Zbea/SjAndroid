package com.hazz.kuangji.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.LoginPresenter
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToastUtils
import kotlinx.android.synthetic.main.mine_activity_register.*


class RegisterActivity : BaseActivity(), LoginContract.LoginView, TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        if(s.toString().contains(" ")){
            val replace = s.toString().replace(" ", "")
            mEtNickName.setText(replace)
            mEtNickName.setSelection(replace.length)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    override fun loginSuccess(msg: UserInfo) {


    }

    override fun sendSms(msg: String) {
        mDialog!!.dismiss()
        ToastUtils.showToast(this, getString(R.string.mine_send_success))
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


    private var mLoginPresenter: LoginPresenter = LoginPresenter(this)
    private var countDownTimer: CountDownTimer? = null


    override fun layoutId(): Int {
        return R.layout.mine_activity_register
    }

    override fun initData() {
        mEtNickName.addTextChangedListener(this)
        tv_xieyi.setOnClickListener {
            startActivity(Intent(this,RegistRuleActivity::class.java).putExtra("type",0))

        }
    }

    override fun initView() {
        tv_tologin.setOnClickListener {
            finish()
        }
    }

    override fun start() {
//        mTvAreaCode.setOnClickListener {
//            startActivityForResult(Intent(this, CountryActivity::class.java), 1005)
//
//        }
        tv_get_code.setOnClickListener {
            if (TextUtils.isEmpty(mEtPhoneOrEmial.text.toString())) {
                SToast.showText(getString(R.string.mine_plz_input_phone))
                return@setOnClickListener
            }
            if (mEtPhoneOrEmial.text.toString().length!=11) {
                SToast.showText(getString(R.string.phone_length_fail))
                return@setOnClickListener
            }
            mLoginPresenter.sendSMs(mEtPhoneOrEmial.text.toString().trim())
        }

        mTvRegister.setOnClickListener {
            if(cb.isChecked){
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
                if (TextUtils.isEmpty(mEtDealPassword.text.toString())||TextUtils.isEmpty(mEtDealPasswordConfirm.text.toString())) {
                    SToast.showText(getString(R.string.deal_login_pwd_not_null))
                    return@setOnClickListener
                }
                if (mEtDealPassword.text.toString() != mEtDealPasswordConfirm.text.toString()) {
                    SToast.showText(getString(R.string.daealpwd_not_same))
                    return@setOnClickListener
                }
                if(mEtPassword.text.toString().length<6||mEtPasswordConfirm.text.toString().length<6){
                    SToast.showText(getString(R.string.deal_pwd_not_length))
                    return@setOnClickListener
                }

                mLoginPresenter.regist(mEtNickName.text.toString(), mEtInviteCode.text.toString().trim(), mEtPhoneOrEmial.text.toString().trim(),
                        mEtCheckCode.text.toString().trim(), mEtPasswordConfirm.text.toString().trim(),
                        mEtDealPasswordConfirm.text.toString().trim()
                )

            }else{
                SToast.showText("请阅读勾选用户注册协议")
            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 1005) {
            val areaCode = data?.getStringExtra("countryNumber") ?: "+86"
            mTvAreaCode.text = areaCode
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
    }

}
