package com.hazz.kuangji.mvp.presenter


import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.MyAsset
import com.hazz.kuangji.net.BasePresenter
import com.hazz.kuangji.net.BaseResult
import com.hazz.kuangji.net.Callback
import com.hazz.kuangji.net.RetrofitManager


class AssetPresenter(view: IContractView.AssetView) : BasePresenter<IContractView.AssetView>(view) {

    fun myAsset() {

        val login = RetrofitManager.service.myAsset()

        doRequest(login, object : Callback<MyAsset>(view) {
            override fun failed(tBaseResult: BaseResult<MyAsset>): Boolean {

                return false
            }

            override fun success(tBaseResult: BaseResult<MyAsset>) {
                tBaseResult.data?.let { view.myAsset(it) }
            }

        }, false)

    }

}