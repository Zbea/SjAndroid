package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.ExchangeOrder
import com.hazz.kuangji.mvp.model.bean.Node
import com.hazz.kuangji.mvp.model.bean.Shenfen
import com.hazz.kuangji.mvp.model.bean.UploadModel
import com.hazz.kuangji.net.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class NodePresenter(view: IContractView.NodeView) : BasePresenter<IContractView.NodeView>(view) {


    fun getNode() {


        val login = RetrofitManager.service.getNode()

        doRequest(login, object : Callback<Node>(view) {
            override fun failed(tBaseResult: BaseResult<Node>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Node>) {
                view.getNode(tBaseResult.data!!)
            }

        }, true)

    }



    fun getShenfen() {


        val login = RetrofitManager.service.getShenfen()

        doRequest(login, object : Callback<Shenfen>(view) {
            override fun failed(tBaseResult: BaseResult<Shenfen>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Shenfen>) {
                view.getShenfen(tBaseResult.data!!)
            }

        }, true)

    }


    fun upImage(file:String) {



        var fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), File(file));

        var requestBody = MultipartBody.Part.createFormData("file",  "header_image.png", fileBody)

        val exchange = RetrofitManager.service.upHeaderImage(requestBody)

        doRequest(exchange, object : Callback<UploadModel>(view) {


            override fun failed(tBaseResult: BaseResult<UploadModel>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<UploadModel>) {
                tBaseResult.data?.let { view.setHeader(it) }
            }

        }, true)

    }



}