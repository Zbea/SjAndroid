package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Cluster
import com.hazz.kuangji.net.*


class ClusterPresenter(view: IContractView.IClusterView) : BasePresenter<IContractView.IClusterView>(view) {

    fun getCluster() {

        val login = RetrofitManager.service.getClusters()

        doRequest(login, object : Callback<Cluster>(view) {
            override fun failed(tBaseResult: BaseResult<Cluster>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Cluster>) {
                tBaseResult.data?.let { view.getLists(it) }
            }

        }, true)

    }


    fun commitCluster(map: HashMap<String,String>) {

        val order = RetrofitManager.service.commitCluster(map)

        doRequest(order, object : Callback<Any>(view) {

            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                view.onSuccess()
            }

        }, true)

    }





}