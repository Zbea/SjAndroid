package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AccelerateInfo
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.net.*

class AcceleratePresenter(view: IContractView.IAccelerateView) : BasePresenter<IContractView.IAccelerateView>(view) {


    fun getAccelerateInfo(id: String) {

        val accelerate = RetrofitManager.service.getAccelerateInfo(id)

        doRequest(accelerate, object : Callback<AccelerateInfo>(view) {
            override fun failed(tBaseResult: BaseResult<AccelerateInfo>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<AccelerateInfo>) {
                tBaseResult.data?.let { view?.getAccelerateInfo(it) }
            }

        }, false)

    }

    fun commitAccelerate(map: HashMap<String,String>) {

        val accelerate = RetrofitManager.service.commitAccelerate(map)

        doRequest(accelerate, object : Callback<Contract>(view) {
            override fun failed(tBaseResult: BaseResult<Contract>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Contract>) {
                tBaseResult.data?.order_id?.let { view?.onSuccess(it) }
            }

        }, false)

    }

}