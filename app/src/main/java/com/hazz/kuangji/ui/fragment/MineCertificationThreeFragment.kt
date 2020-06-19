package com.hazz.kuangji.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Certification
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.mvp.presenter.CertificationPresenter
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import kotlinx.android.synthetic.main.fragment_mine_certification_one.*
import kotlinx.android.synthetic.main.fragment_mine_certification_one.tv_get_code
import kotlinx.android.synthetic.main.fragment_mine_certification_three.*

class MineCertificationThreeFragment : BaseFragment()  ,View.OnClickListener,IContractView.ICertificationView{

    private val Certification:String="Certification"
    private var mCertificationPresenter=CertificationPresenter(this)
    private lateinit var mCertification: Certification
    private var countDownTimer: CountDownTimer? = null

    override fun sendSms(msg: String) {
    }

    override fun commit() {

    }

    public fun newInstance(certification: Certification ):MineCertificationThreeFragment
    {
        var bundle = Bundle()
        bundle.putSerializable(Certification, certification);
        var mineCertificationOneFragment = MineCertificationThreeFragment();
        mineCertificationOneFragment.arguments = bundle;
        return mineCertificationOneFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCertification= arguments?.getSerializable(Certification) as Certification
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine_certification_three
    }

    override fun initView() {

        btn_commit.setOnClickListener(this)

    }

    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when(v)
        {
            btn_commit->{

            }
        }
    }




}