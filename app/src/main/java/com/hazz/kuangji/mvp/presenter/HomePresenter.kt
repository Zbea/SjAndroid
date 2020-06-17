package com.hazz.kuangji.mvp.presenter


import android.util.Log
import android.util.Pair
import com.google.gson.Gson
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils


class HomePresenter(view: IContractView.HomeView) : BasePresenter<IContractView.HomeView>(view) {

    fun getHome() {


        val login = RetrofitManager.service.getHome()

        doRequest(login, object : Callback<Home>(view) {
            override fun failed(tBaseResult: BaseResult<Home>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Home>) {
                tBaseResult.data?.let { view.getHome(it) }
                Log.i("sj",Gson().toJson(tBaseResult))
            }

        }, true)

    }

    fun sign() {


        val login = RetrofitManager.service.sign()

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.zuyongSucceed(tBaseResult.msg)
            }

        }, true)

    }

    fun zuyong(coin: String, product_id: String, trade_password: String, amount: String
               , contractor: String, mobile: String, address: String

    ) {


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("coin", coin),
                Pair.create<Any, Any>("product_id", product_id),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
                Pair.create<Any, Any>("amount", amount),
                Pair.create<Any, Any>("contractor", contractor),
                Pair.create<Any, Any>("mobile", mobile),
                Pair.create<Any, Any>("address", address)
        )

//        Log.i("sj",body.toString())

        val login = RetrofitManager.service.zuyong(body)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.zuyongSucceed(tBaseResult.msg)
            }

        }, true)

    }
}