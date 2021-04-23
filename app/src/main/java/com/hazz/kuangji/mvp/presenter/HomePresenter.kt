package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
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
            }

        }, false)

    }

    fun zuyong(product_id: String, trade_password: String, amount: String, contractor: String) {


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("product_id", product_id),
                Pair.create<Any, Any>("contractor", contractor),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
                Pair.create<Any, Any>("amount", amount)
        )

        val login = RetrofitManager.service.zuyong(body)

        doRequest(login, object : Callback<Contract>(view) {
            override fun failed(tBaseResult: BaseResult<Contract>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Contract>) {
                tBaseResult.data?.order_id?.let { view.zuyongSucceed(it) }
            }

        }, true)

    }


}