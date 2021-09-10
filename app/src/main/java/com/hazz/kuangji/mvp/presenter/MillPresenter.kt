package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class MillPresenter(view: IContractView.IMillView) : BasePresenter<IContractView.IMillView>(view) {

    fun mill() {

        val login = RetrofitManager.service.getMill()

        doRequest(login, object : Callback<Mill>(view) {
            override fun failed(tBaseResult: BaseResult<Mill>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Mill>) {
                tBaseResult.data?.let { view.getMill(it) }
            }

        }, false)

    }


    fun getEargings(id: String,type:Int) {

        val eargings = if (type==2){
            RetrofitManager.service.getMillEarnings2(id)
        }
        else{
            RetrofitManager.service.getMillEarnings(id)
        }
        doRequest(eargings, object : Callback<List<MillEarningsList>>(view) {
            override fun failed(tBaseResult: BaseResult<List<MillEarningsList>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<MillEarningsList>>) {
                view.getEarningsList(tBaseResult.data!!)
            }

        }, true)

    }

}