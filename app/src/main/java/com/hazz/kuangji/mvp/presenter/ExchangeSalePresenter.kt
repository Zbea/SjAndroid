package com.hazz.kuangji.mvp.presenter

import android.text.TextUtils
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.net.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class ExchangeSalePresenter(view: IContractView.IExchangeSaleView) : BasePresenter<IContractView.IExchangeSaleView>(view) {

    fun getExchange() {

        val exchange = RetrofitManager.serviceNew.getExchange()

        doRequest(exchange, object : Callback<Exchange>(view) {


            override fun failed(tBaseResult: BaseResult<Exchange>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Exchange>) {
                view.getExchange(tBaseResult.data!!)
            }

        }, true)

    }


    fun commitOrder(file: String,path: String, typeCoin: String, num: String, price: String, typePay: String) {

        var fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(file));
        var requestBody = MultipartBody.Part.createFormData("file", "image.png", fileBody)

        var map = HashMap<String, String>()
        map["typeCoin"] = typeCoin
        map["num"] = num
        map["price"] = price
        map["typePay"] = typePay
        map["path"] = path
        doRequest(if (!TextUtils.isEmpty(file)) RetrofitManager.service.commitOrderSale(map, requestBody) else RetrofitManager.service.commitOrderSale(map), object : Callback<ExchangeOrder>(view) {


            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                tBaseResult.data?.let { view.commit(it) }
            }

        }, true)

    }


    fun commitOrder(price: String, num: String, typePay: String, typeCoin: String, bankCode: String, bankName: String, bankType: String) {

        var map = HashMap<String, String>()
        map["typeCoin"] = typeCoin
        map["num"] = num
        map["price"] = price
        map["typePay"] = typePay
        map["bankCode"] = bankCode
        map["bankName"] = bankName
        map["bankType"] = bankType

        val exchange = RetrofitManager.service.commitOrderSale(map)

        doRequest(exchange, object : Callback<ExchangeOrder>(view) {


            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                tBaseResult.data?.let { view.commit(it) }
            }

        }, true)

    }


}