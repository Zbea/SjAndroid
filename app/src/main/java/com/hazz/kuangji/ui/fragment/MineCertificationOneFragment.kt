package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.view.View
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.UserInfo
import com.hazz.kuangji.mvp.presenter.CertificationPresenter
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.fragment_mine_certification_one.*
import kotlinx.android.synthetic.main.fragment_mine_certification_one.tv_get_code

class MineCertificationOneFragment : BaseFragment() , IContractView.ICertificationView ,View.OnClickListener{

    private val PHONE:String="PHONE"
    private var mCertificationPresenter=CertificationPresenter(this)
    private var mPhone=""
    private var countDownTimer: CountDownTimer? = null

    override fun sendSms(msg: String) {
        if (mView==null)return
        mDialog?.dismiss()
        SToast.showText(msg)
        showCountDownView()
    }


    override fun commit() {

    }


    public fun newInstance():MineCertificationOneFragment
    {
//        var bundle = Bundle()
//        bundle.putString(PHONE, phone);
        var mineCertificationOneFragment = MineCertificationOneFragment();
//        mineCertificationOneFragment.arguments = bundle;
        return mineCertificationOneFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        mPhone= arguments?.getString(PHONE).toString()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine_certification_one
    }

    override fun initView() {
        var mobile = SPUtil.getString("mobile")
        if (!mobile.isNullOrEmpty() && mobile!="null")
        {
            tv_phone.setText(mobile)
        }
        btn_next.setOnClickListener(this)
        tv_get_code.setOnClickListener(this) 
    }

    override fun lazyLoad() {
    }


    override fun onClick(v: View?) {
        when(v)
        {
            tv_get_code->{
                val mobile=tv_phone.text.toString()
                if (mobile.isNullOrEmpty())
                {
                    SToast.showText("请输入手机号")
                    return
                }
                mCertificationPresenter.sendSMs(mobile)
            }
            btn_next->{
                mPhone=et_code.text.toString()
                if (mPhone.isNullOrEmpty())
                {
                    SToast.showText("请输入验证码")
                    return
                }

                fragmentManager?.beginTransaction()
                        ?.add(R.id.fl_content, MineCertificationTwoFragment().newInstance(mPhone))
                        ?.addToBackStack(null)
                        ?.commit()
            }
        }
    }

    private fun showCountDownView() {
        if (tv_get_code==null)return
        tv_get_code.isEnabled = false
        tv_get_code.isClickable = false
        countDownTimer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onFinish() {
                tv_get_code?.isEnabled = true
                tv_get_code?.isClickable = true
                tv_get_code?.text = getString(R.string.mine_get_check_code)
            }

            override fun onTick(millisUntilFinished: Long) {
                tv_get_code?.text = "${millisUntilFinished / 1000}s"
            }
        }.start()

    }

}