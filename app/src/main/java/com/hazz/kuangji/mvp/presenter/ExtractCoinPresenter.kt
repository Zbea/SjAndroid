package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.model.ExtractRule
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils


class ExtractCoinPresenter(view: IContractView.IExtractView) :
    BasePresenter<IContractView.IExtractView>(view) {

    fun extract(
        amount: String,
        coin: String,
        trade_password: String,
        external_wallet_address: String
    ) {

        val body = RequestUtils.getBody(

            Pair.create<Any, Any>("amount", amount),
            Pair.create<Any, Any>("coin", coin),
            Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
            Pair.create<Any, Any>("address", external_wallet_address)

        )


        val extract = RetrofitManager.service.extract(body)

        doRequest(extract, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.extractSucceed(
                    tBaseResult.msg
                )
            }

        }, true)

    }

    fun record(page: String) {

        val record = RetrofitManager.service.extractRecord(page)

        doRequest(record, object : Callback<ExtractRecord>(view) {
            override fun failed(tBaseResult: BaseResult<ExtractRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExtractRecord>) {
                view.extractRecord(tBaseResult.data!!)
            }

        }, true)

    }

    fun cancel(id: String) {

        val cancel = RetrofitManager.service.cancelExtract(id)

        doRequest(cancel, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.extractSucceed(tBaseResult.msg)
            }

        }, true)

    }

    fun rule() {

        val rule = RetrofitManager.service.extractRule()

        doRequest(rule, object : Callback<ExtractRule>(view) {
            override fun failed(tBaseResult: BaseResult<ExtractRule>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExtractRule>) {
                view.extractRule(tBaseResult.data!!)
            }

        }, true)

    }

}