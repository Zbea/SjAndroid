package com.hazz.kuangji.mvp.presenter


import android.util.Pair
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Sms
import com.hazz.kuangji.mvp.model.bean.UserInfo
import com.hazz.kuangji.net.*
import com.hazz.kuangji.utils.Utils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class CertificationPresenter(view: IContractView.ICertificationView) : BasePresenter<IContractView.ICertificationView>(view) {



    fun sendSMs(mobile: String) {


        val login = RetrofitManager.serviceSms.sendCode(mobile)


        doRequest1(login, object :Observer<Sms>{
            override fun onComplete() {
                view.sendSms("发送成功")
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Sms) {
            }

            override fun onError(e: Throwable) {
                view.sendSms("发送失败")
            }

        },true)

    }




}