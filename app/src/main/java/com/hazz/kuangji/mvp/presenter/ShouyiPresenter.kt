package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.InComing
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class ShouyiPresenter(view: IContractView.ShouyiView) : BasePresenter<IContractView.ShouyiView>(view) {

    fun shouyi() {



        val login = RetrofitManager.service.inComing()

        doRequest(login, object : Callback<InComing>(view) {
            override fun failed(tBaseResult: BaseResult<InComing>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<InComing>) {
                view.inComing(tBaseResult.data!!)
            }

        }, true)

    }

}