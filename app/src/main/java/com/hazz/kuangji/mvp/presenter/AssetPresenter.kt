package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class AssetPresenter(view: IContractView.IAssetView) : BasePresenter<IContractView.IAssetView>(view) {

    fun myAsset(boolean: Boolean) {

        val myAsset = RetrofitManager.service.myAsset()

        doRequest(myAsset, object : Callback<List<Asset>>(view) {
            override fun failed(tBaseResult: BaseResult<List<Asset>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<Asset>>) {
                tBaseResult.data?.let { view.myAsset(it) }
            }

        }, boolean)

    }

    fun getAssetCoin(coin: String) {

        val myAsset = RetrofitManager.service.getAssetCoinList(coin)

        doRequest(myAsset, object : Callback<List<AssetCoin>>(view) {
            override fun failed(tBaseResult: BaseResult<List<AssetCoin>>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<List<AssetCoin>>) {
                tBaseResult.data?.let { view.getAssetCoinList(it) }
            }

        }, true)

    }


}