package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class MsgPresenter(view: IContractView.MsgView) : BasePresenter<IContractView.MsgView>(view) {


    fun getMsg() {

        val msg = RetrofitManager.service.getMsg()

        doRequest(msg, object : Callback<Msg>(view) {
            override fun failed(tBaseResult: BaseResult<Msg>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Msg>) {
                tBaseResult.data?.let { view?.getMsg(it) }
            }

        }, false)

    }

    fun getMsgDetails(id:String) {

        val msgDetails = RetrofitManager.service.getMsgDetails(id)

        doRequest(msgDetails, object : Callback<Msg.MsgBean>(view) {
            override fun failed(tBaseResult: BaseResult<Msg.MsgBean>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Msg.MsgBean>) {
                tBaseResult.data?.let { view?.getMsgDetails(it) }
            }

        }, false)

    }



}