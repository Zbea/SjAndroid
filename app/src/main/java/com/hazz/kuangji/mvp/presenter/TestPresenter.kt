package com.hazz.kuangji.mvp.presenter


import android.util.Log
import android.util.Pair
import com.google.gson.Gson
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.Home
import com.hazz.kuangji.mvp.model.bean.Charge
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils


class TestPresenter(view: LoginContract.HomeView) : BasePresenter<LoginContract.HomeView>(view) {

    fun getTest() {


        val login = RetrofitManager.serviceTest.charge()

        doRequest(login, object : Callback<Charge>(view) {
            override fun failed(tBaseResult: BaseResult<Charge>): Boolean {
                Log.i("sjerror",Gson().toJson(tBaseResult))
                return false
            }

            override fun success(tBaseResult: BaseResult<Charge>) {
                Log.i("sj",Gson().toJson(tBaseResult))
            }

        }, true)

    }


}