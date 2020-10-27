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
import com.hazz.kuangji.ui.activity.mine.ContractDetailsActivity
import com.hazz.kuangji.ui.activity.mine.ContractManagerActivity
import com.hazz.kuangji.ui.adapter.CoinAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.iv_msg
import kotlinx.android.synthetic.main.fragment_mill.recycle_view
import kotlinx.android.synthetic.main.fragment_mill.toolbar
import java.math.RoundingMode

class MillFragment : BaseFragment(), IContractView.kuangjiView {

    private var mMillPresenter:MillPresenter= MillPresenter(this)
    private var mAdapter: CoinAdapter?=null
    private var datas:List<Mill.MachineListBean.ListBean> = ArrayList()

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

        datas=msg.machine_list.list
        mAdapter?.setNewData(datas)

        var countNum="0" //矿机总量
        for (item in datas)
        {
            countNum=BigDecimalUtil.add(countNum,item.buy_storage,4,RoundingMode.HALF_UP)
        }
        tv_number?.text=countNum
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
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            var contract=datas[position]
            if (contract.hide_contract=="0")
            {
                startActivity(Intent(activity, ContractDetailsActivity::class.java).putExtra("contract_code",contract.id)
                        .putExtra("contract_sign",contract.is_sign))
            }
        }

    }

    override fun lazyLoad() {
        mMillPresenter.kuangji()
    }




}
