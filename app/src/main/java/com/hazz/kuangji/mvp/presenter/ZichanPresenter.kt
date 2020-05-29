package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.MyAsset
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class ZichanPresenter(view: LoginContract.ZichanView) : BasePresenter<LoginContract.ZichanView>(view) {

    fun myAsset() {



        val login = RetrofitManager.service.myAsset()

        doRequest(login, object : Callback<MyAsset>(view) {
            override fun failed(tBaseResult: BaseResult<MyAsset>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<MyAsset>) {
                view.myAsset(tBaseResult.data!!)
            }

        }, true)

    }

}