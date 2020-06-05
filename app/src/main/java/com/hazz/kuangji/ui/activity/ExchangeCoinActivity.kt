package com.hazz.kuangji.ui.activity

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.presenter.ExchangeCoinPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_exchange_coin.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeCoinActivity : BaseActivity(), IContractView.IExchangeCoinView {

    private var mMineExchangeCoinPresenter=ExchangeCoinPresenter(this)
    private var type=0//0为u转f ，1为f转u
    private val uStr="USDT"
    private val fStr="FIL"

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
                var count=s.toString().toInt()
                tv_num_fil.text=count.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        ll_transition.setOnClickListener {
            if (type==0)
            {
                type=1
                tv_u_f.text="$fStr  转  $uStr"
                tv_usdt.text="$fStr"
                tv_price_usdt_title.text="$fStr 单价："
                tv_price_usdt.text="￥100"
                tv_count_usdt_title.text="$fStr 总额："
                tv_count_usdt.text="100"

                tv_fil.text="$uStr"
                tv_price_fil_title.text="$uStr 单价："
                tv_price_fil.text="￥200"
                tv_count_fil_title.text="$uStr 总额："
                tv_count_fil.text="200"
            }
            else
            {
                type=0;
                tv_u_f.text="$uStr  转  $fStr"
                tv_usdt.text="$uStr"
                tv_price_usdt_title.text="$uStr 单价："
                tv_price_usdt.text="￥200"
                tv_count_usdt_title.text="$uStr 总额："
                tv_count_usdt.text="200"

                tv_fil.text="$fStr"
                tv_price_fil_title.text="$fStr 单价："
                tv_price_fil.text="￥100"
                tv_count_fil_title.text="$fStr 总额："
                tv_count_fil.text="100"
            }
        }


    }

    override fun initData() {
    }
    override fun start() {
    }


}