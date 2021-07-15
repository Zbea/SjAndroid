package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ValueAdd
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager

/**
 * 增值服务
 */
class ValueAddPresenter(view: IContractView.IValueAddView) : BasePresenter<IContractView.IValueAddView>(view) {


    fun getList() {

        val list = RetrofitManager.service.getValueList()

        doRequest(list, object : Callback<ValueAdd>(view) {
            override fun failed(tBaseResult: BaseResult<ValueAdd>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ValueAdd>) {
                tBaseResult.data?.let {
                    view.getList(it)
                }
            }

        }, true)

    }

    fun extract(id:String,pwd:String) {

        val extract = RetrofitManager.service.extractValue(id,pwd)

        doRequest(extract, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.onSuccess(tBaseResult.msg)
            }

        }, true)

    }



}