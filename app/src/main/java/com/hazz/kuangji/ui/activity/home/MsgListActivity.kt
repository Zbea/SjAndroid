package com.hazz.kuangji.ui.activity.home

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Msg
import com.hazz.kuangji.mvp.presenter.MsgPresenter
import com.hazz.kuangji.ui.adapter.MsgAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list_wihte.*


class MsgListActivity : BaseActivity(), IContractView.MsgView {


    override fun getMsg(msg: List<Msg>) {
        mAdapter?.setNewData(msg)
    }


    private var mCoinPresenter: MsgPresenter = MsgPresenter(this)

    override fun layoutId(): Int {
        return R.layout.activity_list_wihte
    }

    override fun initData() {
        StatusBarUtil.darkMode(this)
        mCoinPresenter.getMsg()
    }


    private var mAdapter: MsgAdapter?=null
    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(getString(R.string.gonggao))
                .setOnLeftIconClickListener {finish() }


        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = MsgAdapter(R.layout.item_msg, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

    }
    override fun start() {


    }


}
