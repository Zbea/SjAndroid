package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class CertificationPresenter(view: IContractView.ICertificationView) : BasePresenter<IContractView.ICertificationView>(view) {



    fun sendSMs(mobile: String) {


        val sms = RetrofitManager.service.sendCode(mobile,"3")
        doRequest(sms, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {
                view.sendSms("发送失败")
                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.sendSms("发送成功")
            }

        }, true)

    }

    fun commitCertification(map: HashMap<String,String>,front:String,opposite:String,hand:String) {

        var fileLists=ArrayList<MultipartBody.Part>()
        fileLists.add(MultipartBody.Part.createFormData("front", "image.png", RequestBody.create(MediaType.parse("multipart/form-data"), File(front))))
        fileLists.add(MultipartBody.Part.createFormData("opposite", "image.png", RequestBody.create(MediaType.parse("multipart/form-data"), File(opposite))))
        fileLists.add(MultipartBody.Part.createFormData("hand", "image.png", RequestBody.create(MediaType.parse("multipart/form-data"), File(hand))))

        doRequest(RetrofitManager.service.commitCertification(map, fileLists), object : Callback<Any>(view) {


            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                tBaseResult.data?.let { view.commit() }
            }

        }, true)

    }


}