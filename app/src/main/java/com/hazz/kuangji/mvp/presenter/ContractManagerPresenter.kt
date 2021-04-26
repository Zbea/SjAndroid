package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Contract
import com.hazz.kuangji.net.*
import com.hazz.kuangji.net.Callback1
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.InputStream


class ContractManagerPresenter(view: IContractView.IContractManagerView) : BasePresenter<IContractView.IContractManagerView>(view) {


    fun getContracts(boolean: Boolean) {


        val login = RetrofitManager.service.getContracts()

        doRequest(login, object : Callback<List<Contract>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Contract>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Contract>>) {
                tBaseResult.data?.let { view.getContracts(it) }
            }

        }, boolean)

    }

    fun getContracts(code:String,boolean: Boolean) {


        val login = RetrofitManager.service.getContracts(code)

        doRequest(login, object : Callback<List<Contract>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Contract>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Contract>>) {
                tBaseResult.data?.let { view.getContracts(it) }
            }

        }, boolean)

    }

    fun setSign(code:String,type:String,file:File) {

        var requestBody = MultipartBody.Part.createFormData("file",  "signing", RequestBody.create(MediaType.parse("multipart/form-data"), file))
        val login = RetrofitManager.service.upSign(code,type,requestBody)

        doRequest(login, object : Callback<Contract>(view) {
            override fun failed(tBaseResult: BaseResult<Contract>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Contract>) {
                tBaseResult.data?.let { view.setSign(it) }
            }

        }, true)

    }

    fun downPdf(type: String,id:String) {

        val down = RetrofitManager.service.downPdf(type,id)

        doRequest1(down, object : Callback1<ResponseBody>(view) {
            override fun failed(tBaseResult: ResponseBody): Boolean {
                TODO("Not yet implemented")
            }
            override fun success(iss: ResponseBody) {
                view.downPdf(iss)
            }
        }, true)

    }


}