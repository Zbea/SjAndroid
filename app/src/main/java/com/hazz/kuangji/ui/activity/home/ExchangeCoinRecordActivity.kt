package com.hazz.kuangji.ui.activity.home

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.ExchangeRecord
import com.hazz.kuangji.mvp.presenter.ExchangeRecordPresenter
import com.hazz.kuangji.ui.adapter.ExchangeCoinRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar

class ExchangeCoinRecordActivity : BaseActivity(), IContractView.IExchangeRecordView {

    private var mExchangeRecordPresenter=ExchangeRecordPresenter(this)
    private var mAdapter: ExchangeCoinRecordAdapter? =null;


    override fun setListView(data: List<ExchangeRecord>) {
        mAdapter?.setNewData(data)
    }

    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initView() {

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle("兑换明细")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ExchangeCoinRecordAdapter(R.layout.item_exchange_coin_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )


    }

    override fun initData() {
    }
    override fun start() {
        mExchangeRecordPresenter.getCoinList()
    }





}