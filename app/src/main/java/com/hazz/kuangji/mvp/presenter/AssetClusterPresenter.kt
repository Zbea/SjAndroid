package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.*
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class AssetClusterPresenter(view: IContractView.IAssetClusterView) : BasePresenter<IContractView.IAssetClusterView>(view) {

    fun getCluster(boolean: Boolean) {

        val login = RetrofitManager.service.getAssetCluster()

        doRequest(login, object : Callback<AssetCluster>(view) {
            override fun failed(tBaseResult: BaseResult<AssetCluster>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<AssetCluster>) {
                tBaseResult.data?.let { view.getCluster(it) }
            }

        }, boolean)

    }

    fun getClusterEarnings(page: String) {

        val login = RetrofitManager.service.getAssetEarningsList(page)

        doRequest(login, object : Callback<AssetClusterEarningsDetails>(view) {
            override fun failed(tBaseResult: BaseResult<AssetClusterEarningsDetails>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<AssetClusterEarningsDetails>) {
                tBaseResult.data?.let { view.getList(it)}
            }

        }, false)

    }


    fun extractCluster(map:HashMap<String,String>) {

        val login = RetrofitManager.service.extractCluster(map)

        doRequest(login, object : Callback<Any>(view) {
            override fun failed(tBaseResult: BaseResult<Any>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<Any>) {
                tBaseResult.data?.let { view.onSuccess()}
            }

        }, true)

    }

    fun extractClusterRecord(){


        val login = RetrofitManager.service.extractClusterList()

        doRequest(login, object : Callback<ExtractRecord>(view) {
            override fun failed(tBaseResult: BaseResult<ExtractRecord>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<ExtractRecord>) {
                view.getExtractList(tBaseResult.data!!)
            }

        }, true)

    }


}