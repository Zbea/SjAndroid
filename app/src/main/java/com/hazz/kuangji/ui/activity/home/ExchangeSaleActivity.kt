package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.Exchange
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.mvp.presenter.CertificationInfoPresenter
import com.hazz.kuangji.mvp.presenter.ExchangeSalePresenter
import com.hazz.kuangji.ui.activity.mine.MineCertificationActivity
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SPUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
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
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class ExchangeSaleActivity : BaseActivity(), IContractView.IExchangeSaleView {

    private var typeCoin:String="usdt"//1为USDT,2为FIL
    private var typePay:String="zfb"//1为微信2为支付宝3为银行卡
    private var data: Exchange?=null
    private var currentPrice="0"
    private var money="0"
    private var max="0"
    private var amount=""
    private val mMineExchangePresenter=ExchangeSalePresenter(this)

    override fun getExchange(data: Exchange) {
        this.data =data
        currentPrice=data.usdtPrice
        max=data.usdtNum
        tv_price.text="￥"+currentPrice
        tv_amount.text=max
    }

    override fun commit(data: ExchangeOrder) {
    }

    private var textWatcher=object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            amount=s.toString()
            money= BigDecimalUtil.mul(amount,currentPrice)
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
                .setTitle("一键卖币")
                .setOnLeftIconClickListener {finish() }
                .setRightText("明细")
                .setRightTextIsShow(true)
                .setOnRightClickListener {
                    var intent= Intent(this, ExchangeRecordActivity::class.java)
                    intent.putExtra("type",2)
                    startActivity(intent)
                }

        rg_buy.setOnCheckedChangeListener { group, checkedId ->
            clearView()
            if (checkedId==R.id.rb_left)
            {
                tv_edit_title.text="USDT"
                typeCoin="usdt"
                max= data?.usdtNum.toString()
                currentPrice= data?.usdtPrice.toString()
                tv_price.text="￥"+currentPrice
                et_num.hint="输入卖出数量(最低"+Constants.BUY_MIN+")"
                tv_amount.text=max
            }
            if (checkedId==R.id.rb_right)
            {
                tv_edit_title.text="FIL"
                typeCoin="fil"
                max= data?.filNum.toString()
                currentPrice = data?.filPrice.toString()
                et_num.hint="输入卖出数量(最低"+Constants.SALE_MIN+")"
                tv_price.text="￥"+currentPrice
                tv_amount.text=max
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
        et_num.hint="输入卖出数量(最低"+Constants.BUY_MIN+")"

        tv_commit.setOnClickListener {
            if (data==null)
            {
                SToast.showText("数据加载失败")
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(amount)){
                SToast.showText("请输入卖出数量")
                return@setOnClickListener
            }

            if(amount.toInt()<if (typeCoin=="usdt")Constants.BUY_MIN else Constants.SALE_MIN){
                SToast.showText("最少卖${if (typeCoin=="usdt")Constants.BUY_MIN else Constants.SALE_MIN}")
                return@setOnClickListener
            }
            if(!BigDecimalUtil.compare(max,amount)){
                SToast.showText("卖出数量不能超过自己拥有的数量")
                return@setOnClickListener
            }

            if (data!=null&&currentPrice!="0")
            {
                val intent=Intent(this, ExchangeOrderSaleCommitActivity::class.java)
                intent.putExtra("price",currentPrice)
                intent.putExtra("amount",amount)
                intent.putExtra("money",money)
                intent.putExtra("typePay",typePay)
                intent.putExtra("typeCoin",typeCoin)
                intent.putExtra("exchange",data)
                startActivity(intent)
            }
        }

    }

    override fun initData() {
        EventBus.getDefault().register(this)
    }
    override fun start() {
        mMineExchangePresenter.getExchange()
    }

    //清空输入的内容
    private fun clearView()
    {
        tv_price_total.text=""
        et_num.setText("")
    }

    private fun setCbView(cbView : ImageView)
    {
        iv_wx_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_zfb_cb.setImageResource(R.mipmap.icon_cb_nor)
        iv_bank_cb.setImageResource(R.mipmap.icon_cb_nor)
        cbView.setImageResource(R.mipmap.icon_cb_select)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event==Constants.CODE_EXCHANGE_SALE)
        {
            if (typeCoin=="usdt")
            {
                max=BigDecimalUtil.subDecimal(data?.usdtNum,amount)
                data?.usdtNum =max
            }else{
                max=BigDecimalUtil.subDecimal(data?.filNum,amount)
                data?.filNum=max
            }
            tv_amount.text=max
            clearView()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }



}