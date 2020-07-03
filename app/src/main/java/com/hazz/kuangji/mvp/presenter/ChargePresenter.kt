package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Charge
import com.hazz.kuangji.mvp.model.ChargeRecord
import com.hazz.kuangji.net.*


class ChargePresenter(view: IContractView.ChargeView) : BasePresenter<IContractView.ChargeView>(view) {

    fun charge(coin: String) {


//        val body = RequestUtils.getBody(
//
//                Pair.create<Any, Any>("amount", amount),
//                Pair.create<Any, Any>("coin", coin),
//                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
//                Pair.create<Any, Any>("external_wallet_address", external_wallet_address)
//
//        )

        val login = RetrofitManager.service.charge(coin)

        doRequest(login, object : Callback<Charge>(view) {
            override fun failed(tBaseResult: BaseResult<Charge>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Charge>) {
                view.getAddress(tBaseResult.data!!)
            }

        }, true)

    }

    fun chargeOmni() {
        doRequest(RetrofitManager.serviceNew.charge(), object : Callback<Charge>(view) {
            override fun failed(tBaseResult: BaseResult<Charge>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Charge>) {
                view.getAddress(tBaseResult.data!!)
            }

        }, true)

    }

    fun chargeRecord(){


        val login = RetrofitManager.service.chargeRecord()

        doRequest(login, object : Callback<ChargeRecord>(view) {
            override fun failed(tBaseResult: BaseResult<ChargeRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ChargeRecord>) {
                view.chargeRecord(tBaseResult.data!!)
            }

        }, true)

    }

}