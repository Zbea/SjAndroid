package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.TransferCoin
import com.hazz.kuangji.mvp.presenter.TransferCoinPresenter
import com.hazz.kuangji.ui.adapter.TransferCoinRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.rc_list
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import kotlinx.android.synthetic.main.activity_transfer_coin_record.*
import kotlinx.android.synthetic.main.activity_transfer_coin_record.invitation_tab_layout

class TransferCoinRecordActivity : BaseActivity(), IContractView.ITransferCoinView {

    private var mExchangeRecordPresenter=TransferCoinPresenter(this)
    private var mAdapter: TransferCoinRecordAdapter? =null;
    private var type=1
    private val titles: Array<String> = arrayOf("转入", "转出")

    override fun commit() {
    }

    override fun getTransferCoin(data: List<TransferCoin>) {
        for (item in data)
            item.type=type
        mAdapter?.setNewData(data)
    }


    override fun layoutId(): Int {
        return R.layout.activity_transfer_coin_record
    }

    override fun initView() {

        mImgBack.setOnClickListener { finish() }

        rg_transfer.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId==R.id.rb_left)
            {
                type=1
            }
            if (checkedId==R.id.rb_right)
            {
                type=0
            }
            mExchangeRecordPresenter.getTransList(type)
        }

        for (a in titles) {
            invitation_tab_layout.addTab(invitation_tab_layout.newTab().setText(a))
        }
        invitation_tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }
            override fun onTabSelected(p0: TabLayout.Tab?) {
                var position = p0!!.position
                when (position) {
                    0 ->type=1
                    1 ->type=0
                }
                mExchangeRecordPresenter.getTransList(type)
            }
        })


        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = TransferCoinRecordAdapter(R.layout.item_transfer_coin_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0)
        )




    }

    override fun initData() {
    }
    override fun start() {
        mExchangeRecordPresenter.getTransList(type)
    }




}