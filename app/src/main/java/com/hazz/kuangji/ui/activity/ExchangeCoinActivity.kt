package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.presenter.ExchangeCoinPresenter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeCoinActivity : BaseActivity(), IContractView.IExchangeCoinView {

    private var mMineExchangeCoinPresenter=ExchangeCoinPresenter(this)
    private var type="UTOF"//0为u转f ，1为f转u
    private val uStr="USDT"
    private val fStr="FIL"
    private lateinit var mData: Exchange
    private lateinit var amount:String
    private lateinit var amountRate:String
    private lateinit var rate:String
    private lateinit var max:String

    override fun getExchange(data: Exchange) {
        mData=data
        max=mData.usdtNum
        rate=mData.usdtrate

        tv_u_f.text="$uStr  转  $fStr"
        tv_usdt.text="$uStr"
        tv_price_usdt_title.text="$uStr 单价："
        tv_price_usdt.text=mData.usdtPrice
        tv_count_usdt_title.text="$uStr 总额："
        tv_count_usdt.text=mData.usdtNum

        tv_fil.text="$fStr"
        tv_price_fil_title.text="$fStr 单价："
        tv_price_fil.text=mData.filPrice
        tv_count_fil_title.text="$fStr 总额："
        tv_count_fil.text=mData.filNum

    }

    override fun commitCoin() {
        SToast.showText("兑换成功")
        et_count_usdt.setText("")
        tv_num_fil.text=""
    }

    override fun layoutId(): Int {
        return R.layout.activity_exchange_coin
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("币币兑换")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener {  finish() }
                .setRightText("兑换明细")
                .setRightTextIsShow(true)
                .setRightTextColor(resources.getColor(R.color.color_white))
                .setOnRightClickListener{
                    startActivity(Intent(this,ExchangeCoinRecordActivity::class.java))
                }

        et_count_usdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                var max=if (type=="UTOF")mData.usdtNum else mData.filNum

//                amount = if (BigDecimalUtil.compare(s.toString(),max)==1) {
//                    SToast.showText("不能超过自己的库存")
//                    return
//                } else{
//                    s.toString()
//                }
                amount=s.toString()
                amountRate=BigDecimalUtil.mul(amount,rate)
                tv_num_fil.text=amountRate
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        ll_transition.setOnClickListener {
            et_count_usdt.setText("")
            tv_num_fil.text=""
            if (type=="UTOF")
            {
//                et_count_usdt.setFilters(arrayOf<InputFilter>(InputFilterMinMax("1", mData.filNum)))
                type="FTOU"
                rate=mData.filrate
                max=mData.filNum

                tv_u_f.text="$fStr  转  $uStr"
                tv_usdt.text="$fStr"
                tv_price_usdt_title.text="$fStr 单价："
                tv_price_usdt.text=mData.filPrice
                tv_count_usdt_title.text="$fStr 总额："
                tv_count_usdt.text=mData.filNum

                tv_fil.text="$uStr"
                tv_price_fil_title.text="$uStr 单价："
                tv_price_fil.text=mData.usdtPrice
                tv_count_fil_title.text="$uStr 总额："
                tv_count_fil.text=mData.usdtNum
            }
            else
            {
//                et_count_usdt.setFilters(arrayOf<InputFilter>(InputFilterMinMax("1", mData.usdtNum)))
                type="UTOF"
                rate=mData.usdtrate
                max=mData.usdtNum

                tv_u_f.text="$uStr  转  $fStr"
                tv_usdt.text="$uStr"
                tv_price_usdt_title.text="$uStr 单价："
                tv_price_usdt.text=mData.usdtPrice
                tv_count_usdt_title.text="$uStr 总额："
                tv_count_usdt.text=mData.usdtNum

                tv_fil.text="$fStr"
                tv_price_fil_title.text="$fStr 单价："
                tv_price_fil.text=mData.filPrice
                tv_count_fil_title.text="$fStr 总额："
                tv_count_fil.text=mData.filNum
            }
        }

        tv_commit.setOnClickListener {
            if (BigDecimalUtil.compare(amount,max)==1)
            {
                SToast.showText("卖出不能超过自己的库存")
                return@setOnClickListener
            }
            if (amount.toInt()<=0)
            {
                SToast.showText("卖出必须大于0")
                return@setOnClickListener
            }
            mMineExchangeCoinPresenter.commitExchangeCoin(type,amount,amountRate)
        }

    }

    override fun initData() {
    }
    override fun start() {
        mMineExchangeCoinPresenter.getExchange()
    }




}