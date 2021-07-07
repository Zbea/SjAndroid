package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Charge
import com.hazz.kuangji.mvp.model.ChargeRecord
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class ChargePresenter(view: IContractView.ChargeView) : BasePresenter<IContractView.ChargeView>(view) {

    fun charge() {


        val login = RetrofitManager.service.charge()

        doRequest(login, object : Callback<Charge>(view) {
            override fun failed(tBaseResult: BaseResult<Charge>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Charge>) {
                view.getAddress(tBaseResult.data!!)
            }

        }, true)

    }


    fun chargeRecord(page:String){


        val record = RetrofitManager.service.chargeRecord(page)

        doRequest(record, object : Callback<ChargeRecord>(view) {
            override fun failed(tBaseResult: BaseResult<ChargeRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ChargeRecord>) {
                view.chargeRecord(tBaseResult.data!!)
            }

        }, true)

    }

}