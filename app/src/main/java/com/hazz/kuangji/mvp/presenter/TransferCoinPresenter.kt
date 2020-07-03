package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.TransferCoin
import com.hazz.kuangji.net.*


class TransferCoinPresenter(view: IContractView.ITransferCoinView) : BasePresenter<IContractView.ITransferCoinView>(view) {


    fun commit(type:String,amount:String,username:String,password:String) {

        var map=HashMap<String,String>()
        map["name"]=username
        map["amount"]=amount
        map["coin"]=type
        map["pwd"]=password

        val commit = RetrofitManager.serviceNew.transAccount(map)

        doRequest(commit, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {
                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.commit()
            }

        }, false)

    }

    fun getTransList(type: Int) {


        val list = RetrofitManager.serviceNew.getTransAccountList(type)

        doRequest(list, object : Callback<List<TransferCoin>>(view) {
            override fun failed(tBaseResult: BaseResult<List<TransferCoin>>): Boolean {
                return false
            }

            override fun success(tBaseResult: BaseResult<List<TransferCoin>>) {
                tBaseResult.data?.let { view.getTransferCoin(it)}
            }

        }, true)

    }



}