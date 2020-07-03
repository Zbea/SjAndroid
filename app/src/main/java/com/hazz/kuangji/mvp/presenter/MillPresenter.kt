package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.Mingxi
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class MillPresenter(view: IContractView.kuangjiView) : BasePresenter<IContractView.kuangjiView>(view) {

    fun kuangji() {



        val login = RetrofitManager.service.kuangji()

        doRequest(login, object : Callback<Mill>(view) {
            override fun failed(tBaseResult: BaseResult<Mill>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Mill>) {
                tBaseResult.data?.let { view.getKuangji(it) }
            }

        }, false)

    }


    fun mingxi(start:String,end:String) {



        val login = RetrofitManager.service.mingxi(start,end)

        doRequest(login, object : Callback<Mingxi>(view) {
            override fun failed(tBaseResult: BaseResult<Mingxi>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Mingxi>) {
                view.getMingxi(tBaseResult.data!!)
            }

        }, true)

    }

}