package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.MinerFILInfo
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils

/**
 * 服务器详情、租用
 */
class MinerRentPresenter(view: IContractView.IMinerRentView) : BasePresenter<IContractView.IMinerRentView>(view) {

    fun getInfo(id:String) {

        val info = RetrofitManager.service.getMinerFilInfo(id)

        doRequest(info, object : Callback<MinerFILInfo>(view) {
            override fun failed(tBaseResult: BaseResult<MinerFILInfo>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<MinerFILInfo>) {
                tBaseResult.data?.let { view.getInfo(it) }
            }

        }, false)

    }

    fun buyRent(product_id: String, trade_password: String, amount: String) {

        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("product_id", product_id),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
                Pair.create<Any, Any>("amount", amount)
        )

        val rent = RetrofitManager.service.commitMiner(body)

        doRequest(rent, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                tBaseResult.msg.let { view.onSucceed(it) }
            }

        }, true)

    }


}