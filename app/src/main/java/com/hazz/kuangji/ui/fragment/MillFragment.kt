package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.Mingxi
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.ui.activity.mill.MillRecordActivity
import com.hazz.kuangji.ui.adapter.CoinAdapter
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.iv_msg
import kotlinx.android.synthetic.main.fragment_mill.recycle_view
import kotlinx.android.synthetic.main.fragment_mill.toolbar

class MillFragment : BaseFragment(), IContractView.kuangjiView {

    private var mMillPresenter:MillPresenter= MillPresenter(this)
    private var mAdapter: CoinAdapter?=null

    override fun getMingxi(msg: Mingxi) {
    }

    override fun getKuangji(msg: Mill) {
        if (mView==null)return
        sl_refresh?.isRefreshing=false
        if(msg.total!=null){
            tv_touzi?.text=msg.total
        }
        if(msg.yesterday!=null){
            tv_shouyi?.text=msg.yesterday
        }
        tv_number?.text=msg.machine_list.list.size.toString()
        mAdapter?.setNewData(msg.machine_list.list)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mill
    }

    override fun initView() {

        var layoutParams: RelativeLayout.LayoutParams= toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        sl_refresh=activity?.findViewById(R.id.sl_refresh_mill)
        sl_refresh?.isRefreshing=true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }

        ll_record.setOnClickListener {
            startActivity(Intent(activity, MillRecordActivity::class.java))
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = CoinAdapter(R.layout.item_mill, null)
        recycle_view.adapter = mAdapter
        mAdapter?.bindToRecyclerView(recycle_view)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        recycle_view.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

    }

    override fun lazyLoad() {
        mMillPresenter.kuangji()
    }




}
