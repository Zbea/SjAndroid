package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.net.*

class ExchangeOrderSaleDetailsPresenter(view:IContractView.IExchangeSaleDetailsView) : BasePresenter<IContractView.IExchangeSaleDetailsView>(view) {

    fun getOrder(code:String) {

        val order = RetrofitManager.service.getOrderSale(code)

        doRequest(order, object : Callback<ExchangeOrder>(view) {


            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                view.getOrder(tBaseResult.data!!)
            }

        }, true)

    }

    fun cancelOrder(code: String) {

        val cancel = RetrofitManager.service.cancelOrderSale(code)

        doRequest(cancel, object : Callback<Any>(view) {

            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.cancelOrder()
            }

        }, true)

    }


}