package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.TibiRecord
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils


class TiBiPresenter(view: IContractView.TibiView) : BasePresenter<IContractView.TibiView>(view) {

    fun tibi(amount: String, coin: String, trade_password: String, external_wallet_address: String
    ) {


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("amount", amount),
                Pair.create<Any, Any>("coin", coin),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
                Pair.create<Any, Any>("external_wallet_address", external_wallet_address)

        )


        val login = RetrofitManager.service.tibi(body)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.tibiSucceed(tBaseResult.msg
                )
            }

        }, true)

    }

    fun tibiRecord(){


        val login = RetrofitManager.service.tibiRecord()

        doRequest(login, object : Callback<TibiRecord>(view) {
            override fun failed(tBaseResult: BaseResult<TibiRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<TibiRecord>) {
                view.tibiRecord(tBaseResult.data!!)
            }

        }, true)

    }

}