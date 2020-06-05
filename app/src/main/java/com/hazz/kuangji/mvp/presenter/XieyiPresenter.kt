package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Xieyi
import com.hazz.kuangji.mvp.model.bean.SignRecord
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class XieyiPresenter(view: IContractView.XieyiView) : BasePresenter<IContractView.XieyiView>(view) {

    fun xieyi(target:String) {



        val login = RetrofitManager.service.xieyi(target)

        doRequest(login, object : Callback<Xieyi>(view) {
            override fun failed(tBaseResult: BaseResult<Xieyi>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Xieyi>) {
                view.xieyi(tBaseResult.data!!)
            }

        }, true)

    }

    fun signRecord() {



        val login = RetrofitManager.service.signRecord()

        doRequest(login, object : Callback<SignRecord>(view) {
            override fun failed(tBaseResult: BaseResult<SignRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<SignRecord>) {
                view.getSignRecord(tBaseResult.data!!)
            }

        }, true)

    }

}