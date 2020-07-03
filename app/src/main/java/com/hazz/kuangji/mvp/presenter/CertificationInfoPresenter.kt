package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Certification
import com.hazz.kuangji.net.*


class CertificationInfoPresenter(view: IContractView.ICertificationInfoView) : BasePresenter<IContractView.ICertificationInfoView>(view) {


    fun getCertification() {


        val login = RetrofitManager.service.getCertification()

        doRequest(login, object : Callback<Certification>(view) {
            override fun failed(tBaseResult: BaseResult<Certification>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Certification>) {
                tBaseResult.data?.let {
                    view.getCertification(it)
                }
            }

        }, false)

    }



}