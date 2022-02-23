package com.hazz.kuangji.ui.activity

import android.text.Html
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Agreement
import com.hazz.kuangji.mvp.presenter.AgreementPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_agreement.*


class AgreementActivity : BaseActivity(), IContractView.IAgreementView {

    private var mAgreementPresenter:AgreementPresenter= AgreementPresenter(this)
    private var type=0

    override fun agreement(msg: Agreement) {
        if (msg.detail!=null)
            tv_rule.text=Html.fromHtml(msg.detail)
    }

    override fun initData() {

    }

    override fun layoutId(): Int {
        return R.layout.activity_agreement
    }


    override fun initView() {
        val toolbar= ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setOnLeftIconClickListener {  finish() }
         type= intent.flags

        if(type==0){
            toolbar.setTitle("用戶註冊協議")
            mAgreementPresenter.agreementUser()
        }else{
            toolbar.setTitle("服務器租賃協議")
            mAgreementPresenter.agreementRent()
        }

    }
    override fun start() {
    }




}
