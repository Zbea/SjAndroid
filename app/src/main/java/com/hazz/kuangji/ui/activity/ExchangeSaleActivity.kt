package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.TextUtils
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.ExchangeRecordBean
import com.hazz.kuangji.mvp.presenter.ExchangePresenter
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_exchange_buy.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeSaleActivity : BaseActivity(), IContractView.IExchangeBuyView {

    var typeCoin:Int=1//1为USDT,2为FIL
    var typePay:Int=1//1为微信2为支付宝3为银行卡
    var mMineExchangePresenter=ExchangePresenter(this)



    override fun layoutId(): Int {
        return R.layout.activity_exchange_sale
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("一键卖币")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }
                .setRightText("卖币明细")
                .setRightTextIsShow(true)
                .setRightTextColor(resources.getColor(R.color.color_white))
                .setOnRightClickListener {
                    var intent= Intent(this,ExchangeRecordActivity::class.java)
                    intent.putExtra("type",1)
                    startActivity(intent)
                }

        rg_buy.setOnCheckedChangeListener { group, checkedId ->

            if (checkedId==R.id.rb_left)
            {
                tv_edit_title.text="USDT"
                typeCoin=1
            }
            if (checkedId==R.id.rb_right)
            {
                tv_edit_title.text="FIL"
                typeCoin=2
            }

        }


        tv_commit.setOnClickListener {
            if(TextUtils.isEmpty(et_num.text.toString())){
                SToast.showText("请输入卖出数量")
                return@setOnClickListener
            }
            startActivity(Intent(this,ExchangeOrderSaleDetailsActivity::class.java))
        }

        ll_wx.setOnClickListener{
            typePay=1
            setCbView(iv_wx_cb)
        }
        ll_zfb.setOnClickListener{
            typePay=2
            setCbView(iv_zfb_cb)
        }
        ll_bank.setOnClickListener{
            typePay=3
            setCbView(iv_bank_cb)
        }


    }

    override fun initData() {
    }
    override fun start() {
    }

    fun setCbView(cbView : ImageView)
    {
        iv_wx_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_zfb_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_bank_cb.setImageResource(R.mipmap.icon_cb_nor)
        cbView.setImageResource(R.mipmap.icon_cb_select)
    }





}