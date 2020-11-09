package com.hazz.kuangji.ui.activity.asset

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.EarningsSource
import com.hazz.kuangji.mvp.presenter.EarningsSourcePresenter
import com.hazz.kuangji.ui.adapter.EarningsSourceAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_charge.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_invitefriends_record.mToolBar


/**
 * @Created by Zbea
 * @fileName FilEarningsSourceActivity
 * @date 2020/11/4 14:31
 * @email xiaofeng9212@126.com
 * @describe 昨日收益来源
 **/
class YesterdayEarningsSourceActivity : BaseActivity(), IContractView.EarningsSourceView {

    private val filSourcePresenter=EarningsSourcePresenter(this)
    private var mAdapter: EarningsSourceAdapter? = null

    override fun getList(msg: EarningsSource) {
        mAdapter?.setNewData(msg.list)
    }

    override fun layoutId(): Int {
        return R.layout.activity_earngings_source_list
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle("昨日收益明细")
                .setOnLeftIconClickListener {finish() }


        rg_charge.setOnCheckedChangeListener { group, checkedId ->

            when(checkedId)
            {
                R.id.rb_left->{
                    filSourcePresenter.getUSDTLists()
                }
                R.id.rb_right->{
                    filSourcePresenter.getFilLists()
                }
            }
        }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = EarningsSourceAdapter(R.layout.item_earnings_record, null)
        rc_list.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(rc_list)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

    }

    override fun initData() {
        filSourcePresenter.getFilLists()
    }
    override fun start() {
    }

}
