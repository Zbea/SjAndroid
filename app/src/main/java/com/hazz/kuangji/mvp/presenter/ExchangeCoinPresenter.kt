package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager

class ExchangeCoinPresenter(view:IContractView.IExchangeCoinView) : BasePresenter<IContractView.IExchangeCoinView>(view) {

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


    fun commitExchangeCoin(type:String,amount1:String,amount2:String,password:String) {


        val order = RetrofitManager.serviceNew.commitExchange(type,amount1,amount2,password)

        doRequest(order, object : Callback<Any>(view) {

            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.commitCoin()
            }

        }, true)

    }

}