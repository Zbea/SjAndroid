
package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ClusterOrder
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.ui.activity.mill.ClusterEarningsRecordActivity
import com.hazz.kuangji.ui.activity.mill.MillRecordActivity
import com.hazz.kuangji.ui.adapter.MillAdapter
import com.hazz.kuangji.ui.adapter.MillClusterAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.iv_msg
import kotlinx.android.synthetic.main.fragment_mill.list_mill
import kotlinx.android.synthetic.main.fragment_mill.toolbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.RoundingMode

class MillFragment : BaseFragment(), IContractView.kuangjiView {

    private var mMillPresenter:MillPresenter= MillPresenter(this)
    private var mAdapter: MillAdapter?=null
    private var mClusterAdapter: MillClusterAdapter?=null
    private var datas:List<Mill.MachineListBean.ListBean> = ArrayList()

    override fun getEarningsList(msg: MillEarningsList) {
    }

    override fun getMill(msg: Mill) {
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

    override fun getClusterOrders(item: ClusterOrder) {

        tv_cluster_total.text=item.total
        tv_cluster_yesterday.text=item.yesterday

        mClusterAdapter?.setNewData(item.cluster_list.list)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mill
    }

    override fun initView() {
        EventBus.getDefault().register(this)

        var layoutParams: RelativeLayout.LayoutParams= toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        sl_refresh=activity?.findViewById(R.id.sl_refresh_mill)
        sl_refresh?.isRefreshing=true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }


        rg_mill.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_left->{
                    list_mill.visibility= View.VISIBLE
                    ll_earnings_mill.visibility=View.VISIBLE

                    list_cluster.visibility=View.GONE
                    ll_earnings_cluster.visibility=View.GONE
                }
                R.id.rb_right->{
                    list_mill.visibility= View.GONE
                    ll_earnings_mill.visibility=View.GONE

                    list_cluster.visibility=View.VISIBLE
                    ll_earnings_cluster.visibility=View.VISIBLE
                }
            }

        }


        iv_msg.setOnClickListener {
            startActivity(Intent(activity, MsgListActivity::class.java))
        }

        ll_record.setOnClickListener {
            startActivity(Intent(activity, MillRecordActivity::class.java))
        }

        ll_record_cluster.setOnClickListener {
            startActivity(Intent(activity, ClusterEarningsRecordActivity::class.java))
        }

        list_mill.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = MillAdapter(R.layout.item_mill, null)
        list_mill.adapter = mAdapter
        mAdapter?.bindToRecyclerView(list_mill)
        mAdapter?.setEmptyView(R.layout.fragment_empty)

        val leftRightOffset = DensityUtil.dp2px(15f)
        list_mill.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        list_cluster.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mClusterAdapter = MillClusterAdapter(R.layout.item_mill_cluster, null)
        list_cluster.adapter = mClusterAdapter
        mClusterAdapter?.bindToRecyclerView(list_cluster)
        mClusterAdapter?.setEmptyView(R.layout.fragment_empty)
        list_cluster.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
    }

    override fun lazyLoad() {
        mMillPresenter.kuangji()
        mMillPresenter.getClusterOrders()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_CLUSTER_BUY) {
            mMillPresenter.getClusterOrders()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
