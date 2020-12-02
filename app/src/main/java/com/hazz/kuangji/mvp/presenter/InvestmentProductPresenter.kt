package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetDetails
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.mvp.model.InvestmentProduct
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class InvestmentProductPresenter(view: IContractView.IInvestmentProductView) : BasePresenter<IContractView.IInvestmentProductView>(view) {


    fun getLists() {

        val login = RetrofitManager.service.getInvestmentProducts()

        doRequest(login, object : Callback<List<InvestmentProduct>>(view) {
            override fun failed(tBaseResult: BaseResult<List<InvestmentProduct>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<InvestmentProduct>>) {
                tBaseResult.data?.let { view.getLists(it)}
            }

        }, true)

    }

    fun commitBuy(product:String , amount:String , trade_password:String) {

        val commit = RetrofitManager.service.onInvestmentBuy(product,amount,trade_password)

        doRequest(commit, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                tBaseResult.data?.let { view.onSuccess()}
            }

        }, true)

    }


}