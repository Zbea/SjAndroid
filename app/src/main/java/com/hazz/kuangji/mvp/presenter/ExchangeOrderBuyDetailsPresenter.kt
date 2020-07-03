package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager

class ExchangeOrderBuyDetailsPresenter(view:IContractView.IExchangeOrderBuyView) : BasePresenter<IContractView.IExchangeOrderBuyView>(view) {

    fun getOrder(code:String) {

        val order = RetrofitManager.serviceNew.getOrder(code)

        doRequest(order, object : Callback<ExchangeOrder>(view) {


            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                view.getOrder(tBaseResult.data!!)
            }

        }, true)

    }

    fun commitPay(code: String) {

        val pay = RetrofitManager.serviceNew.commitPay(code)

        doRequest(pay, object : Callback<Any>(view) {


            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.commitPay()
            }

        }, true)

    }

    fun cancelOrder(code: String) {

        val cancel = RetrofitManager.serviceNew.cancelOrder(code)

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