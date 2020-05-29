package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Msg
import com.hazz.kuangji.net.*


class MsgPresenter(view: LoginContract.MsgView) : BasePresenter<LoginContract.MsgView>(view) {


    fun getMsg() {


        val login = RetrofitManager.service.getMsg()

        doRequest(login, object : Callback<List<Msg>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Msg>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Msg>>) {
                view.getMsg(tBaseResult.data!!)
            }

        }, true)

    }



}