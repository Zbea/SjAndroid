package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.net.*


class MainPresenter(view: IContractView.MainView) : BasePresenter<IContractView.MainView>(view) {


    fun getTest() {


        val login = RetrofitManager.serviceTest.getTest()

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {

            }

        }, true)

    }



}