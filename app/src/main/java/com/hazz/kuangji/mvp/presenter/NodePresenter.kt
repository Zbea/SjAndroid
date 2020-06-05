package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Node
import com.hazz.kuangji.mvp.model.bean.Shenfen
import com.hazz.kuangji.net.*


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



    fun getShenfen() {


        val login = RetrofitManager.service.getShenfen()

        doRequest(login, object : Callback<Shenfen>(view) {
            override fun failed(tBaseResult: BaseResult<Shenfen>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Shenfen>) {
                view.getShenfen(tBaseResult.data!!)
            }

        }, true)

    }


}