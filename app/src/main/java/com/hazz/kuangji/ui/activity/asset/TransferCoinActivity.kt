package com.hazz.kuangji.ui.activity.asset

import android.content.Intent
import android.text.TextUtils
import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Exchange
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.mvp.model.TransferCoin
import com.hazz.kuangji.mvp.presenter.ExchangePresenter
import com.hazz.kuangji.mvp.presenter.TransferCoinPresenter
import com.hazz.kuangji.ui.activity.mine.MineExchangePwdActivity
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.SToast
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.utils.Utils
import com.hazz.kuangji.widget.SafeCheckDialog
import kotlinx.android.synthetic.main.activity_exchange_buy.rg_buy
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_commit
import kotlinx.android.synthetic.main.activity_exchange_buy.tv_price
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import kotlinx.android.synthetic.main.activity_transfer_coin.*

class TransferCoinActivity : BaseActivity(), IContractView.IExchangeBuyView , IContractView.ITransferCoinView {

    private var typeCoin:String="usdt"//1为USDT,2为FIL
    private var mData: Exchange?=null
    private var max="0"
    private var amount="0"
    private var mExchangePresenter=ExchangePresenter(this)
    private var mTransferCoinPresenter=TransferCoinPresenter(this)
    private var mPasswordDialog: SafeCheckDialog? = null

    override fun getExchange(data: Exchange) {
        mData =data
        tv_price.text="￥"+data.usdtPrice
        max=data.usdtNum
        tv_count.text=max
    }
    override fun commitOrder(data: ExchangeOrder) {
    }

    override fun commit() {
        SToast.showText("转出成功")
        clearView()
        if (typeCoin=="usdt")
        {
            max=BigDecimalUtil.subDecimal(mData?.usdtNum,amount)
            mData?.usdtNum =max
        }else{
            max=BigDecimalUtil.subDecimal(mData?.filNum,amount)
            mData?.filNum=max
        }
        tv_count.text=max
    }
    override fun getTransferCoin(data: List<TransferCoin>) {
    }



    override fun layoutId(): Int {
        return R.layout.activity_transfer_coin
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("转账")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener {finish() }
                .setRightText("转账明细")
                .setRightTextIsShow(true)
                .setRightTextColor(resources.getColor(R.color.color_white))
                .setOnRightClickListener {
                    startActivity(Intent(this,TransferCoinRecordActivity::class.java))
                }

        rg_buy.setOnCheckedChangeListener { group, checkedId ->
            clearView()
            if (checkedId==R.id.rb_left)
            {
                typeCoin="usdt"
                tv_price.text="￥"+mData?.usdtPrice
                max= mData?.usdtNum.toString()
                tv_count.text=max
            }
            if (checkedId==R.id.rb_right)
            {
                typeCoin="fCoin"
                tv_price.text="￥"+mData?.filPrice
                max= mData?.filNum.toString()
                tv_count.text=max
            }
        }


        tv_commit.setOnClickListener {
            if (mData==null)
            {
                SToast.showText("数据加载失败")
                return@setOnClickListener
            }
            val userName=et_user_name.text.toString()
            if (TextUtils.isEmpty(userName))
            {
                SToast.showText("转入的用户名不能为空")
                return@setOnClickListener
            }
            amount=et_amount.text.toString()

            if (!BigDecimalUtil.compare(max,amount))
            {
                SToast.showText("转出数量不能超过当前拥有")
                return@setOnClickListener
            }

            if (amount.toInt() <= 0) {
                SToast.showText("转出数量必须大于0")
                return@setOnClickListener
            }

            if (mPasswordDialog == null) {
                mPasswordDialog = SafeCheckDialog(this)
                        .setCancelListener { }
                        .setForgetListener {
                            startActivity(Intent(this, MineExchangePwdActivity::class.java))
                        }
                        .setConfirmListener { _, password ->
                            mTransferCoinPresenter.commit(typeCoin, amount, userName, Utils.encryptMD5(password))

                        }.setCancelListener {

                        }
            }
            mPasswordDialog?.show()

        }
    }

    override fun initData() {
    }
    override fun start() {
        mExchangePresenter.getExchange()
    }

    private fun clearView()
    {
        et_amount.setText("")
        et_user_name.setText("")
    }

}