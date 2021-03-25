package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetDetails
import com.hazz.kuangji.mvp.model.Investment
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class InvestmentPresenter(view: IContractView.IInvestmentView) : BasePresenter<IContractView.IInvestmentView>(view) {


    fun getLists(pageNum:String,isShow:Boolean) {

        val login = RetrofitManager.service.getInvestments(pageNum,"0")

        doRequest(login, object : Callback<Investment>(view) {
            override fun failed(tBaseResult: BaseResult<Investment>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Investment>) {
                tBaseResult.data?.let { view.getLists(it)}
            }

        }, isShow)

    }

    fun getListsCompleted(pageNum:String,isShow:Boolean) {

        val login = RetrofitManager.service.getInvestments(pageNum,"1")

        doRequest(login, object : Callback<Investment>(view) {
            override fun failed(tBaseResult: BaseResult<Investment>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Investment>) {
                tBaseResult.data?.let { view.getListsCompleted(it)}
            }

        }, isShow)

    }

    fun outInvestment(id:String,passWord:String) {

        val login = RetrofitManager.service.outInvestment(id,passWord)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.onSuccess()
            }

        }, true)

    }


}