package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Account
import com.hazz.kuangji.mvp.model.UploadModel
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class MyAccountPresenter(view: IContractView.IAccountView) : BasePresenter<IContractView.IAccountView>(view) {


    fun getAccount() {


        val account = RetrofitManager.service.getAccount()

        doRequest(account, object : Callback<Account>(view) {
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

        val header = RetrofitManager.service.upHeaderImage(requestBody)

        doRequest(header, object : Callback<UploadModel>(view) {


            override fun failed(tBaseResult: BaseResult<UploadModel>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<UploadModel>) {
                tBaseResult.data?.let { view.setHeader(it) }
            }

        }, true)

    }



}