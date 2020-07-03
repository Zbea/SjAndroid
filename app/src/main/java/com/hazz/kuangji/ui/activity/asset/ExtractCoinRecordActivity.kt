package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.TibiRecord
import com.hazz.kuangji.mvp.presenter.TiBiPresenter
import com.hazz.kuangji.ui.adapter.ExtractCoinRecordAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.invitefriends_record.mToolBar


class ExtractCoinRecordActivity : BaseActivity(), IContractView.TibiView {
    override fun tibiSucceed(msg: String) {

    }

    override fun tibiRecord(msg: TibiRecord) {
        val list = msg.list
        mAdapter?.setNewData(list)
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
        mTiBiPresenter.tibiRecord()
    }


    private var mAdapter: ExtractCoinRecordAdapter?=null

    private var mTiBiPresenter: TiBiPresenter = TiBiPresenter(this)

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.tibi_record))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ExtractCoinRecordAdapter(R.layout.item_extract_coin, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)

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
    override fun start() {


    }


}
