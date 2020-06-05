package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.presenter.ExchangeOrderBuyDetailsPresenter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeOrderSaleDetailsActivity : BaseActivity(), IContractView.IExchangeOrderBuyView {

    var mExchangeOrderBuyPresenter=ExchangeOrderBuyDetailsPresenter(this)

    override fun layoutId(): Int {
        return R.layout.activity_exchange_order_sale_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("卖币订单信息确认")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }



    }

    override fun initData() {
    }
    override fun start() {
    }


}