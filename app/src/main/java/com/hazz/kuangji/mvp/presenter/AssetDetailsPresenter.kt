package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetClusterEarningsDetails
import com.hazz.kuangji.mvp.model.AssetDetails
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class AssetDetailsPresenter(view: IContractView.AssetFilDetailsView) : BasePresenter<IContractView.AssetFilDetailsView>(view) {


    fun getLists() {

        val login = RetrofitManager.service.getAssetFilDetails()

        doRequest(login, object : Callback<List<AssetDetails>>(view) {
            override fun failed(tBaseResult: BaseResult<List<AssetDetails>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<AssetDetails>>) {
                tBaseResult.data?.let { view.getDetails(it)}
            }

        }, true)

    }

    fun getLists(start:String,end:String) {

        val login = RetrofitManager.service.getAssetFilDetails(start,end)

        doRequest(login, object : Callback<List<AssetDetails>>(view) {
            override fun failed(tBaseResult: BaseResult<List<AssetDetails>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<AssetDetails>>) {
                tBaseResult.data?.let { view.getDetails(it)}
            }

        }, true)

    }

    fun getClusterLists() {

        val login = RetrofitManager.service.getAssetClusterDetails()

        doRequest(login, object : Callback<AssetClusterEarningsDetails>(view) {
            override fun failed(tBaseResult: BaseResult<AssetClusterEarningsDetails>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<AssetClusterEarningsDetails>) {
                tBaseResult.data?.let { view.getClusterDetails(it)}
            }

        }, true)

    }

    fun getClusterLists(start:String,end:String) {

        val login = RetrofitManager.service.getAssetClusterDetails(start,end)

        doRequest(login, object : Callback<AssetClusterEarningsDetails>(view) {
            override fun failed(tBaseResult: BaseResult<AssetClusterEarningsDetails>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<AssetClusterEarningsDetails>) {
                tBaseResult.data?.let { view.getClusterDetails(it)}
            }

        }, true)

    }


}