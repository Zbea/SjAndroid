package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.TransferCoin
import com.hazz.kuangji.mvp.presenter.TransferCoinPresenter
import com.hazz.kuangji.ui.adapter.TransferCoinRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar

class TransferCoinRecordActivity : BaseActivity(), IContractView.ITransferCoinView {

    private var mExchangeRecordPresenter=TransferCoinPresenter(this)
    private var mAdapter: TransferCoinRecordAdapter? =null;

    override fun commit() {
    }

    override fun getTransferCoin(data: List<TransferCoin>) {
        mAdapter?.setNewData(data)
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initView() {

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("转账明细")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = TransferCoinRecordAdapter(R.layout.item_transfer_coin_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0)
        )


    }

    override fun initData() {
    }
    override fun start() {
        mExchangeRecordPresenter.getTransList()
    }




}