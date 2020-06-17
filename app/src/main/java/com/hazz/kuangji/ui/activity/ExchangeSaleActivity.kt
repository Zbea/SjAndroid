package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.ExchangeSalePresenter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_exchange_buy.*
import kotlinx.android.synthetic.main.activity_exchange_buy.et_num
import kotlinx.android.synthetic.main.activity_exchange_buy.iv_bank_cb
import kotlinx.android.synthetic.main.activity_exchange_buy.iv_wx_cb
import kotlinx.android.synthetic.main.activity_exchange_buy.iv_zfb_cb
import kotlinx.android.synthetic.main.activity_exchange_buy.ll_bank
import kotlinx.android.synthetic.main.activity_exchange_buy.ll_wx
import kotlinx.android.synthetic.main.activity_exchange_buy.ll_zfb
import kotlinx.android.synthetic.main.activity_exchange_buy.rg_buy
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_commit
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_edit_title
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_price
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_price_total
import kotlinx.android.synthetic.main.activity_exchange_sale.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeSaleActivity : BaseActivity(), IContractView.IExchangeSaleView {

    private var typeCoin:String="usdt"//1为USDT,2为FIL
    private var typePay:String="wx"//1为微信2为支付宝3为银行卡
    private lateinit var data:Exchange
    private var currentPrice="0"
    private var money="0"
    private lateinit var amount:String
    private var mMineExchangePresenter=ExchangeSalePresenter(this)

    override fun getExchange(data: Exchange) {
        this.data =data
        currentPrice=data.usdtPrice
        amount=data.usdtNum
        tv_price.text="￥"+currentPrice
        tv_amount.text=amount
    }

    override fun commit(data: ExchangeOrder) {
    }

    var textWatcher=object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            money= BigDecimalUtil.mul(s.toString(),currentPrice)
            tv_price_total.text="￥"+money
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

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
                    intent.putExtra("type",2)
                    startActivity(intent)
                }

        rg_buy.setOnCheckedChangeListener { group, checkedId ->
            clearView()
            if (checkedId==R.id.rb_left)
            {
                tv_edit_title.text="USDT"
                typeCoin="usdt"
                amount=data.usdtNum
                currentPrice=data.usdtPrice
                tv_price.text="￥"+currentPrice
                tv_amount.text=amount
            }
            if (checkedId==R.id.rb_right)
            {
                tv_edit_title.text="FIL"
                typeCoin="fil"
                amount=data.filNum
                currentPrice=data.filPrice
                tv_price.text="￥"+currentPrice
                tv_amount.text=amount
            }

        }

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

        et_num.addTextChangedListener(textWatcher)


        tv_commit.setOnClickListener {
            if(TextUtils.isEmpty(et_num.text.toString())){
                SToast.showText("请输入卖出数量")
                return@setOnClickListener
            }

            if(BigDecimalUtil.compare(et_num.text.toString(),"100")==-1){
                SToast.showText("最少卖100")
                return@setOnClickListener
            }
            if(BigDecimalUtil.compare(et_num.text.toString(),amount)==1){
                SToast.showText("卖出数量不能超过自己拥有的数量")
                return@setOnClickListener
            }
            val intent=Intent(this,ExchangeOrderSaleCommitActivity::class.java)
            intent.putExtra("price",currentPrice)
            intent.putExtra("amount",et_num.text.toString())
            intent.putExtra("money",money)
            intent.putExtra("typePay",typePay)
            intent.putExtra("typeCoin",typeCoin)
            startActivity(intent)
            clearView()
        }

    }

    override fun initData() {
    }
    override fun start() {
        mMineExchangePresenter.getExchange()
    }


    //清空输入的内容
    fun clearView()
    {
        tv_price_total.text=""
        et_num.setText("")
    }


    fun setCbView(cbView : ImageView)
    {
        iv_wx_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_zfb_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_bank_cb.setImageResource(R.mipmap.icon_cb_nor)
        cbView.setImageResource(R.mipmap.icon_cb_select)
    }




}