package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Charge
import com.hazz.kuangji.mvp.model.ChargeRecord
import com.hazz.kuangji.mvp.presenter.ChargePresenter
import com.hazz.kuangji.ui.adapter.ChargeRecordAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_invitefriends_record.mToolBar


class ChargeRecordActivity : BaseActivity(), IContractView.ChargeView {
    override fun getAddress(msg: Charge) {

    }


    override fun chargeRecord(msg: ChargeRecord) {
        val list = msg.list
        mAdapter?.setNewData(list)
    }


    override fun layoutId(): Int {
        return R.layout.activity_list_wihte
    }

    override fun initData() {
        mChargePresenter.chargeRecord()
    }


    private var mAdapter: ChargeRecordAdapter?=null

    private var mChargePresenter: ChargePresenter = ChargePresenter(this)

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.charge_record))
                .setOnLeftIconClickListener {  finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ChargeRecordAdapter(R.layout.item_extract_coin, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

    }
    override fun start() {


    }


}
