package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Config
import com.hazz.kuangji.mvp.model.Sms
import com.hazz.kuangji.net.*
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


class ConfigPresenter(view: IContractView.IConfigView) : BasePresenter<IContractView.IConfigView>(view) {


    fun getConfig() {


        val login = RetrofitManager.service.getConfig()

        doRequest(login, object : Callback<Config>(view) {
            override fun failed(tBaseResult: BaseResult<Config>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Config>) {
                tBaseResult.data?.let { view.getConfig(it) }
            }

        }, false)

    }

    fun getDownload() {

        val login = RetrofitManager.service.getDownloadCode()

        doRequest1(login, object : Observer<Config> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: Config) {
                view.getConfig(t)
            }
            override fun onError(e: Throwable) {
            }

        },false)

    }



}