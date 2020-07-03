package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Exchange
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils

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

        var map=HashMap<String,String>()
        map["type"]=type
        map["amount1"]=amount1
        map["amount2"]=amount2
        map["pwd"]= Utils.encryptMD5(password)

        val order = RetrofitManager.serviceNew.commitExchange(map)

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