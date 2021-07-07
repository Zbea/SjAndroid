package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Agreement
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class AgreementPresenter(view: IContractView.IAgreementView) : BasePresenter<IContractView.IAgreementView>(view) {

    fun agreementUser() {

        val login = RetrofitManager.service.getAgreementUser()

        doRequest(login, object : Callback<Agreement>(view) {
            override fun failed(tBaseResult: BaseResult<Agreement>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Agreement>) {
                view.agreement(tBaseResult.data!!)
            }

        }, true)

    }

    fun agreementRent() {

        val login = RetrofitManager.service.getAgreementRent()

        doRequest(login, object : Callback<Agreement>(view) {
            override fun failed(tBaseResult: BaseResult<Agreement>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Agreement>) {
                view.agreement(tBaseResult.data!!)
            }

        }, true)

    }


}