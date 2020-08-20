package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.mvp.model.UploadModel
import com.hazz.kuangji.net.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class ContractManagerPresenter(view: IContractView.IContractManagerView) : BasePresenter<IContractView.IContractManagerView>(view) {


    fun getContracts() {


        val login = RetrofitManager.service.getContracts()

        doRequest(login, object : Callback<List<Contract>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Contract>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Contract>>) {
                tBaseResult.data?.let { view.getContracts(it) }
            }

        }, true)

    }

    fun setSign(code:String,file:File) {

        var requestBody = MultipartBody.Part.createFormData("file",  "signing", RequestBody.create(MediaType.parse("multipart/form-data"), file))
        val login = RetrofitManager.service.upSign(code,requestBody)

        doRequest(login, object : Callback<Contract>(view) {
            override fun failed(tBaseResult: BaseResult<Contract>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Contract>) {
                tBaseResult.data?.let { view.setSign(it) }
            }

        }, true)

    }


}