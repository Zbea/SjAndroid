package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Account
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.mvp.model.Node
import com.hazz.kuangji.mvp.model.UploadModel
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

    fun getCertification() {


        val login = RetrofitManager.service.getCertification()

        doRequest(login, object : Callback<Certification>(view) {
            override fun failed(tBaseResult: BaseResult<Certification>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Certification>) {
                tBaseResult.data?.let { view.getCertification(it) }
            }

        }, false)

    }

    fun getAccount() {


        val login = RetrofitManager.service.getAccount()

        doRequest(login, object : Callback<Account>(view) {
            override fun failed(tBaseResult: BaseResult<Account>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Account>) {
                tBaseResult.data?.let { view.getAccount(it) }
            }

        }, false)

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