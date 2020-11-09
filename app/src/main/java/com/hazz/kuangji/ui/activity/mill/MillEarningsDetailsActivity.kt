package com.hazz.kuangji.ui.activity.mill

import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.MillEarningsDetails
import com.hazz.kuangji.mvp.presenter.MillEarningsDetailsPresenter
import com.hazz.kuangji.ui.adapter.EarningDetailsAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_mill_earnings_details.*


/**
 * @Created by Zbea
 * @fileName MillEarningsDetailsActivity
 * @date 2020/11/6 14:31
 * @email xiaofeng9212@126.com
 * @describe 矿机收益详细
 **/
class MillEarningsDetailsActivity : BaseActivity(), IContractView.EarningsDetailsView{

    private val presenter=MillEarningsDetailsPresenter(this)
    private var mAdapter: EarningDetailsAdapter? = null
    private var id=""

    override fun getDetails(msg: List<MillEarningsDetails>) {
       mAdapter?.setNewData(msg)
    }

    override fun layoutId(): Int {
        return R.layout.activity_mill_earnings_details
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("收益详情")
                .setOnLeftIconClickListener {finish() }

        id=intent.getStringExtra("orderId")

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = EarningDetailsAdapter(R.layout.item_mill_earnings_details, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)

    }

    override fun initData() {
        presenter.getLists(id)
    }
    override fun start() {
    }



}
