package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Exchange
import com.hazz.kuangji.mvp.model.ExchangeOrder
import com.hazz.kuangji.net.*

class ExchangePresenter(buyView:IContractView.IExchangeBuyView) : BasePresenter<IContractView.IExchangeBuyView>(buyView) {

    fun getExchange() {

        val exchange = RetrofitManager.serviceNew.getExchange()

        doRequest(exchange, object : Callback<Exchange>(view) {


            override fun failed(tBaseResult: BaseResult<Exchange>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Exchange>) {
                view.getExchange(tBaseResult.data!!)
            }

        }, true)

    }


    fun commitOrder(coin:String,num:String,pay:String,price:String) {


        val order = RetrofitManager.serviceNew.commitOrder(coin,num,pay,price)

        doRequest(order, object : Callback<ExchangeOrder>(view) {

            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                view.commitOrder(tBaseResult.data!!)
            }

        }, true)

    }

}