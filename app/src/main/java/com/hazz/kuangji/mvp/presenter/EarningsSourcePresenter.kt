package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.EarningsSource
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class EarningsSourcePresenter(view: IContractView.EarningsSourceView) : BasePresenter<IContractView.EarningsSourceView>(view) {


    fun getFilLists() {



        val login = RetrofitManager.service.filEarningsList()

        doRequest(login, object : Callback<EarningsSource>(view) {
            override fun failed(tBaseResult: BaseResult<EarningsSource>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<EarningsSource>) {
                tBaseResult.data?.let { view.getList(it) }
            }

        }, true)

    }

    fun getUSDTLists() {

        val login = RetrofitManager.service.usdtEarningsList()

        doRequest(login, object : Callback<EarningsSource>(view) {
            override fun failed(tBaseResult: BaseResult<EarningsSource>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<EarningsSource>) {
                tBaseResult.data?.let { view.getList(it) }
            }

        }, true)

    }

}