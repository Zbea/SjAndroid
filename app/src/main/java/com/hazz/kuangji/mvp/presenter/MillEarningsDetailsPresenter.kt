package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class MillEarningsDetailsPresenter(view: IContractView.EarningsDetailsView) : BasePresenter<IContractView.EarningsDetailsView>(view) {


    fun getLists(orderId:String) {

        val login = RetrofitManager.service.getEarningsDetails(orderId)

        doRequest(login, object : Callback<List<MillEarningsDetails>>(view) {
            override fun failed(tBaseResult: BaseResult<List<MillEarningsDetails>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<MillEarningsDetails>>) {
                tBaseResult.data?.let { view.getDetails(it)}
            }

        }, true)

    }



}