package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class HomePresenter(view: IContractView.HomeView) : BasePresenter<IContractView.HomeView>(view) {

    fun getHome() {


        val login = RetrofitManager.service.getHome()

        doRequest(login, object : Callback<Home>(view) {
            override fun failed(tBaseResult: BaseResult<Home>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Home>) {
                tBaseResult.data?.let { view.getHome(it) }
            }

        }, false)

    }



}