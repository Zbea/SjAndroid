package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AccelerateInfo
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager

class AcceleratePresenter(view: IContractView.IAccelerateView) : BasePresenter<IContractView.IAccelerateView>(view) {


    fun getAccelerateInfo(id: String) {

        val accelerate = RetrofitManager.service.getAccelerateInfo(id)

        doRequest(accelerate, object : Callback<AccelerateInfo>(view) {
            override fun failed(tBaseResult: BaseResult<AccelerateInfo>): Boolean {
                view.onFail(tBaseResult.msg)
                return false
            }

            override fun success(tBaseResult: BaseResult<AccelerateInfo>) {
                tBaseResult.data?.let { view?.getAccelerateInfo(it) }
            }

        }, false)

    }

    fun commitAccelerate(map: HashMap<String,String>) {

        val accelerate = RetrofitManager.service.commitAccelerate(map)

        doRequest(accelerate, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                tBaseResult.msg?.let { view?.onSuccess(it) }
            }

        }, false)

    }

}