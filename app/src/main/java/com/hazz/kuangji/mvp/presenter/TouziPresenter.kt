package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Touzi
import com.hazz.kuangji.net.*
import android.util.Pair
import com.hazz.kuangji.mvp.model.bean.TouziRecord
import com.hazz.kuangji.utils.Utils

class TouziPresenter(view: LoginContract.TouziView) : BasePresenter<LoginContract.TouziView>(view) {

    fun touxiList() {


        val login = RetrofitManager.service.touxiList()

        doRequest(login, object : Callback<Touzi>(view) {
            override fun failed(tBaseResult: BaseResult<Touzi>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Touzi>) {
                view.touziList(tBaseResult.data!!)
            }

        }, true)

    }

    fun touzi(coin:String,product_id:String,trade_password:String){


        val body = RequestUtils.getBody(

                Pair.create<Any, Any>("coin", coin),
                Pair.create<Any, Any>("trade_password", Utils.encryptMD5(trade_password)),
                Pair.create<Any, Any>("product_id", product_id)

        )

        val login = RetrofitManager.service.touzi(body)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.touziConfirm(tBaseResult.msg)
            }

        }, true)

    }


    fun touziRecord() {


        val login = RetrofitManager.service.touziRecord()

        doRequest(login, object : Callback<TouziRecord>(view) {
            override fun failed(tBaseResult: BaseResult<TouziRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<TouziRecord>) {
                view.touziRecord(tBaseResult.data!!)
            }

        }, true)

    }

}