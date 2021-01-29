package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class MillPresenter(view: IContractView.kuangjiView) : BasePresenter<IContractView.kuangjiView>(view) {

    fun kuangji() {



        val login = RetrofitManager.service.getMill()

        doRequest(login, object : Callback<Mill>(view) {
            override fun failed(tBaseResult: BaseResult<Mill>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Mill>) {
                tBaseResult.data?.let { view.getMill(it) }
            }

        }, false)

    }



    fun mingxi(start:String,end:String) {



        val login = RetrofitManager.service.getMillEarnings(start,end)

        doRequest(login, object : Callback<MillEarningsList>(view) {
            override fun failed(tBaseResult: BaseResult<MillEarningsList>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<MillEarningsList>) {
                view.getEarningsList(tBaseResult.data!!)
            }

        }, true)

    }

}