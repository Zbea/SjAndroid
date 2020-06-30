package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.net.*


class MsgPresenter(view: IContractView.MsgView) : BasePresenter<IContractView.MsgView>(view) {


    fun getMsg() {


        val login = RetrofitManager.service.getMsg()

        doRequest(login, object : Callback<List<Msg>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Msg>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Msg>>) {
                tBaseResult.data?.let { view.getMsg(it) }
            }

        }, false)

    }



}