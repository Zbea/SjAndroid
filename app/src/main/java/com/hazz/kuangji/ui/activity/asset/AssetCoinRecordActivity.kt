package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Asset
import com.hazz.kuangji.mvp.model.AssetCoin
import com.hazz.kuangji.mvp.presenter.AssetPresenter
import com.hazz.kuangji.ui.adapter.AssetCoinRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_list.*

/**
 * @Created by Zbea
 * @fileName MillAccelerateActivity
 * @date 2021/7/2 17:31
 * @email xiaofeng9212@126.com
 * @describe 查看币种
 **/
class AssetCoinRecordActivity : BaseActivity(), IContractView.IAssetView {

    private var mAdapter: AssetCoinRecordAdapter? = null
    private var mAssetPresenter: AssetPresenter = AssetPresenter(this)
    private var coin="USDT"

    override fun myAsset(items: List<Asset>) {
        TODO("Not yet implemented")
    }
    override fun getAssetCoinList(items: List<AssetCoin>) {
        mAdapter?.setNewData(items)
    }

    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
        coin=intent.getStringExtra("coin")
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle("可用明细")
            .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = AssetCoinRecordAdapter(R.layout.item_asset_coin, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)

    }

    override fun start() {
        mAssetPresenter.getAssetCoin(coin)

    }



}
