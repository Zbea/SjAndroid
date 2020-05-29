package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Coin
import com.hazz.kuangji.mvp.model.bean.Friends
import com.hazz.kuangji.net.*


class CoinPresenter(view: LoginContract.CoinView) : BasePresenter<LoginContract.CoinView>(view) {


    fun getCoin() {


        val login = RetrofitManager.service.getCoin()

        doRequest(login, object : Callback<List<Coin>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Coin>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Coin>>) {
                view.getCoin(tBaseResult.data!!)
            }

        }, true)

    }
    fun getFriends() {


        val login = RetrofitManager.service.getFriends()

        doRequest(login, object : Callback<Friends>(view) {
            override fun failed(tBaseResult: BaseResult<Friends>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Friends>) {
                view.getFriends(tBaseResult.data!!)
            }

        }, true)

    }


}