package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Touzi
import com.hazz.kuangji.mvp.model.TouziRecord
import com.hazz.kuangji.mvp.presenter.TouziPresenter
import com.hazz.kuangji.ui.adapter.TouziRecordAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.invitefriends_record.mToolBar


class TouziRecordActivity : BaseActivity(), IContractView.TouziView {
    override fun touziList(msg: Touzi) {

    }

    override fun touziConfirm(msg: String) {
    }

    override fun touziRecord(msg: TouziRecord) {
        mAdapter?.setNewData(msg.list)
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
        mTouziPresenter.touziRecord()
    }


    private var mAdapter: TouziRecordAdapter?=null
    private var mTouziPresenter: TouziPresenter = TouziPresenter(this)


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.touzi_record))
                .setToolBarBgRescource(R.drawable.bg_main_gradient)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = TouziRecordAdapter(R.layout.item_touzi_record, null)
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
