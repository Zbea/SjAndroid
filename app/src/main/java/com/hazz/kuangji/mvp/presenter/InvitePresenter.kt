package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Invite
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class InvitePresenter(view: IContractView.IInviteView) : BasePresenter<IContractView.IInviteView>(view) {


    fun getInvite() {


        val invite = RetrofitManager.service.getInviteRecord()

        doRequest(invite, object : Callback<List<Invite>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Invite>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Invite>>) {
                view.getInviteRecord(tBaseResult.data!!)
            }

        }, true)

    }


}