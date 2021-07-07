package com.hazz.kuangji.ui.activity.mill

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.adapter.MillRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_list.*


class MillEargingsListActivity : BaseActivity(), IContractView.IMillView {

    private var mMillPresenter: MillPresenter = MillPresenter(this)
    private var id=""
    private var mAdapter: MillRecordAdapter? = null

    override fun getMill(msg: Mill) {
    }

    override fun getEarningsList(msg: List<MillEarningsList>) {
        mAdapter?.setNewData(msg)
    }


    override fun layoutId(): Int {
        return R.layout.activity_mill_eargings_list
    }

    override fun initData() {
        id=intent.getStringExtra("orderId")
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("收益明细")
                .setOnLeftIconClickListener {finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = MillRecordAdapter(R.layout.item_mill_eargings, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)

    }

    override fun start() {
        mMillPresenter.getEargings(id)
    }


}
