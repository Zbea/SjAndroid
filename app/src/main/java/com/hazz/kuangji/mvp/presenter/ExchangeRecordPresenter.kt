package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExchangeRecord
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager

class ExchangeRecordPresenter(view:IContractView.IExchangeRecordView) : BasePresenter<IContractView.IExchangeRecordView>(view) {

    fun getList(type:String) {


        val list = RetrofitManager.serviceNew.getOrderBuyList(type)

        doRequest(list, object : Callback<List<ExchangeRecord>>(view) {
            override fun failed(tBaseResult: BaseResult<List<ExchangeRecord>>): Boolean {
                return false
            }

            override fun success(tBaseResult: BaseResult<List<ExchangeRecord>>) {
                view.setListView(tBaseResult.data!!)
            }

        }, true)

    }

    fun getCoinList() {


        val list = RetrofitManager.serviceNew.getExchangeList()

        doRequest(list, object : Callback<List<ExchangeRecord>>(view) {
            override fun failed(tBaseResult: BaseResult<List<ExchangeRecord>>): Boolean {
                return false
            }

            override fun success(tBaseResult: BaseResult<List<ExchangeRecord>>) {
                view.setListView(tBaseResult.data!!)
            }

        }, true)

    }

}