package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangePresenter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_exchange_buy.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeBuyActivity : BaseActivity(), IContractView.IExchangeBuyView {

    private var typeCoin:String="usdt"//1为USDT,2为FIL
    private var typePay:String="wx"//1为微信2为支付宝3为银行卡
    private var mData:Exchange?=null
    private var currentPrice="0"
    private var money="0"
    private var amount="0"
    private var mExchangePresenter=ExchangePresenter(this)

    override fun getExchange(data: Exchange) {
        mData =data
        currentPrice=data.usdtPrice
        tv_price.text="￥"+currentPrice
    }

    override fun commitOrder(data: ExchangeOrder) {
        tv_price_total.text=""
        et_num.setText("")
        var intent=Intent(this,ExchangeOrderBuyDetailsActivity::class.java)
        intent.putExtra("code",data.order_code)
        startActivity(intent)
    }

    var textWatcher=object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                amount=s.toString()
                money=BigDecimalUtil.mul(amount,currentPrice)
                tv_price_total.text="￥"+money
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_buy
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("一键买币")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }
                .setRightText("买币明细")
                .setRightTextIsShow(true)
                .setRightTextColor(resources.getColor(R.color.color_white))
                .setOnRightClickListener {
                    var intent= Intent(this,ExchangeRecordActivity::class.java)
                    intent.putExtra("type",1)
                    startActivity(intent)
                }

        rg_buy.setOnCheckedChangeListener { group, checkedId ->
            tv_price_total.text=""
            et_num.setText("")
            if (checkedId==R.id.rb_left)
            {
                tv_edit_title.text="USDT"
                typeCoin="usdt"
                currentPrice= mData?.usdtPrice.toString()
                tv_price.text="￥"+currentPrice
            }
            if (checkedId==R.id.rb_right)
            {
                tv_edit_title.text="FIL"
                typeCoin="fil"
                currentPrice=mData?.filPrice.toString()
                tv_price.text="￥"+currentPrice
            }
        }

        et_num.addTextChangedListener(textWatcher)

        ll_wx.setOnClickListener{
            typePay="wx"
            setCbView(iv_wx_cb)
        }
        ll_zfb.setOnClickListener{
            typePay="zfb"
            setCbView(iv_zfb_cb)
        }
        ll_bank.setOnClickListener{
            typePay="bank"
            setCbView(iv_bank_cb)
        }

        tv_commit.setOnClickListener {
            if (mData==null)
            {
                SToast.showText("数据加载失败")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(amount)){
                SToast.showText("请输入买入数量")
                return@setOnClickListener
            }
            if(amount.toInt()<1){
                SToast.showText("最少购买1个")
                return@setOnClickListener
            }
            mExchangePresenter.commitOrder(typeCoin,amount,typePay,currentPrice)

        }
    }

    override fun initData() {
    }
    override fun start() {
        mExchangePresenter.getExchange()
    }

    private fun setCbView(cbView : ImageView)
    {
        iv_wx_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_zfb_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_bank_cb.setImageResource(R.mipmap.icon_cb_nor)
        cbView.setImageResource(R.mipmap.icon_cb_select)
    }


}