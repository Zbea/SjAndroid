package com.hazz.kuangji.mvp.presenter

import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Exchange
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.net.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class ExchangeSalePresenter(view:IContractView.IExchangeSaleView) : BasePresenter<IContractView.IExchangeSaleView>(view) {

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


    fun commitOrder(file:String,typeCoin:String,num:String,price:String,typePay:String) {


        var fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(file));

        var requestBody = MultipartBody.Part.createFormData("file",  "image.png", fileBody)


        val exchange = RetrofitManager.service.commitOrderSale(price,num,typePay,typeCoin,requestBody)

        doRequest(exchange, object : Callback<ExchangeOrder>(view) {


            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
                tBaseResult.data?.let { view.commit(it) }
            }

        }, true)

    }


//    fun commitOrder(file:String,typeCoin:String,num:String,price:String,typePay:String) {
//
//        var fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(file));
//        var requestBody = MultipartBody.Part.createFormData("file",  "image.png", fileBody)
//
//        var parts: List<MultipartBody.Part> = ArrayList()
//        parts.toMutableList()
//        parts
//
//        val body = RequestUtils.getBody(
//
//                Pair.create<Any, Any>("typeCoin", typeCoin),
//                Pair.create<Any, Any>("num", num),
//                Pair.create<Any, Any>("price", price),
//                Pair.create<Any, Any>("typePay", typePay)
//
//        )
//
////        var fileBody = RequestBody.create(MediaType.parse("image/*"), File(file));
////        var requestBody = MultipartBody.Builder()
////                .setType(MultipartBody.FORM)
////                .addFormDataPart("typeCoin", typeCoin)
////                .addFormDataPart("num", num)
////                .addFormDataPart("price", price)
////                .addFormDataPart("typePay", typePay)
////                .addFormDataPart("file", if (typePay=="wx") "微信收款二维码.png" else "支付宝收款二维码.png", fileBody)
//
//
//        val exchange = RetrofitManager.service.commitOrderSale(body,requestBody)
//
//        doRequest(exchange, object : Callback<ExchangeOrder>(view) {
//
//
//            override fun failed(tBaseResult: BaseResult<ExchangeOrder>): Boolean {
//
//                return false
//            }
//
//            override fun success(tBaseResult: BaseResult<ExchangeOrder>) {
//
//            }
//
//        }, true)
//
//    }

    fun commitOrder(price:String,num:String,typePay:String,typeCoin:String,bankCode:String,bankName:String,bankType:String) {



        var requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)

                .addFormDataPart("typeCoin", typeCoin)
                .addFormDataPart("num", num)
                .addFormDataPart("price", price)
                .addFormDataPart("typePay", typePay)
                .addFormDataPart("bankCode", bankCode)
                .addFormDataPart("bankName", bankName)
                .addFormDataPart("bankType", bankType)

        val exchange = RetrofitManager.service.commitOrderSale(price, num, typePay, typeCoin, bankCode, bankName, bankType)

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