package com.hazz.kuangji.ui.activity.home

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Charge
import com.hazz.kuangji.mvp.model.ChargeRecord
import com.hazz.kuangji.mvp.presenter.ChargePresenter
import com.hazz.kuangji.ui.adapter.ChargeRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*


class ChargeRecordActivity : BaseActivity(), IContractView.ChargeView {

    private var mAdapter: ChargeRecordAdapter?=null
    private var mChargePresenter: ChargePresenter = ChargePresenter(this)
    private var page = 1

    override fun getAddress(msg: Charge) {
    }
    override fun chargeRecord(msg: ChargeRecord) {
        if (page == 1) {
            mAdapter?.setNewData(msg.list)
        } else {
            mAdapter?.addData(msg.list)
        }
        if (msg.list.size < Constants.PAGE_SIZE) {
            mAdapter?.loadMoreEnd(true)
        } else {
            mAdapter?.loadMoreComplete()
        }
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
        mChargePresenter.chargeRecord(page.toString())
    }


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.charge_record))
                .setOnLeftIconClickListener {  finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ChargeRecordAdapter(R.layout.item_asset_coin, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        mAdapter?.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            rc_list.postDelayed(Runnable {
                var size = mAdapter?.data?.size
                if (page * Constants.PAGE_SIZE == size) {
                    page += 1
                    mChargePresenter.chargeRecord(page.toString())
                }
            }, 1000)
        }, rc_list)

    }
    override fun start() {
    }


}
