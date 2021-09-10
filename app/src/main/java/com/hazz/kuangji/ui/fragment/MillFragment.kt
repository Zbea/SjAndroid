package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidkun.xtablayout.XTabLayout
import com.google.gson.Gson
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.activity.mill.MillEargingsListActivity
import com.hazz.kuangji.ui.adapter.MillFILAdapter
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.tab
import kotlinx.android.synthetic.main.fragment_mill.tv_top
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

class MillFragment : BaseFragment(), IContractView.IMillView {

    private var mMillPresenter: MillPresenter = MillPresenter(this)
    private var mAdapterFIL: MillFILAdapter? = null
    private var mMill: Mill? = null
    private var type=0

    override fun getMill(msg: Mill) {
        if (mView == null) return
        if (msg == null) return

        sl_refresh?.isRefreshing = false
        mMill = msg

        when(type){
            0->{
                tv_number?.text = msg?.fil?.power
                tv_earnings?.text = msg?.fil?.totalRevenue
                tv_yesterday?.text = msg?.fil?.yesterdayRevenue
                mAdapterFIL?.setNewData(msg?.fil?.list)
            }
            1->{
                tv_number?.text = msg?.cluster?.power
                tv_earnings?.text = msg?.cluster?.totalRevenue
                tv_yesterday?.text = msg?.cluster?.yesterdayRevenue
                mAdapterFIL?.setNewData(msg?.cluster?.list)
            }
            2->{
                tv_number?.text = msg?.fil2?.power
                tv_earnings?.text = msg?.fil2?.totalRevenue
                tv_yesterday?.text = msg?.fil2?.yesterdayRevenue
                mAdapterFIL?.setNewData(mMill?.fil2?.list)
            }
        }

    }


    override fun getEarningsList(msg: List<MillEarningsList>) {
        TODO("Not yet implemented")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mill
    }

    override fun initView() {

        EventBus.getDefault().register(this)

        var layoutParams: RelativeLayout.LayoutParams =
            tv_top.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        tv_top.layoutParams = layoutParams

        sl_refresh = activity?.findViewById(R.id.sl_refresh_mill)
        sl_refresh?.isRefreshing = true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            lazyLoad()
        }

        val leftRightOffset = DensityUtil.dp2px(5f)
        list.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapterFIL = MillFILAdapter(R.layout.item_mill_fil, null)
        list.adapter = mAdapterFIL
        mAdapterFIL?.bindToRecyclerView(list)
        mAdapterFIL?.setEmptyView(R.layout.fragment_empty)
        list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
        mAdapterFIL?.setOnItemChildClickListener { adapter, view, position ->
            if (view.id==R.id.btn_earning){
                when(type){
                    0->{
                        startActivity(Intent(activity, MillEargingsListActivity::class.java).putExtra("type",type)
                            .putExtra("orderId", mMill?.fil?.list?.get(position)?.id))
                    }
                    1->{
                        startActivity(Intent(activity, MillEargingsListActivity::class.java).putExtra("type",type)
                            .putExtra("orderId", mMill?.cluster?.list?.get(position)?.id))
                    }
                    2->{
                        startActivity(Intent(activity, MillEargingsListActivity::class.java).putExtra("type",type)
                            .putExtra("orderId", mMill?.fil2?.list?.get(position)?.id))
                    }
                }

            }
        }

        initTab()

    }

    override fun lazyLoad() {
        mMillPresenter.mill()
    }


    /**
     * 动态获取设置tab索引
     */
    private fun initTab() {

        tab?.newTab()?.setText("服务器")?.let { it -> tab?.addTab(it) }
        tab?.newTab()?.setText("集群")?.let { it -> tab?.addTab(it) }
        tab?.newTab()?.setText("新方案")?.let { it -> tab?.addTab(it) }

        tab?.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                if (tab?.text.toString() == "服务器") {
                    if (mMill?.fil == null) return
                    type=0
                    mAdapterFIL?.setNewData(mMill?.fil?.list)
                    tv_number?.text = mMill?.fil?.power
                    tv_earnings?.text = mMill?.fil?.totalRevenue
                    tv_yesterday?.text = mMill?.fil?.yesterdayRevenue

                } else if (tab?.text.toString() == "集群") {
                    if (mMill?.fil == null) return
                    type=1
                    mAdapterFIL?.setNewData(mMill?.cluster?.list)
                    tv_number?.text = mMill?.cluster?.power
                    tv_earnings?.text = mMill?.cluster?.totalRevenue
                    tv_yesterday?.text = mMill?.cluster?.yesterdayRevenue

                } else {
                    if (mMill?.fil2 == null) return
                    type=2
                    mAdapterFIL?.setNewData(mMill?.fil2?.list)
                    tv_number?.text = mMill?.fil2?.power
                    tv_earnings?.text = mMill?.fil2?.totalRevenue
                    tv_yesterday?.text = mMill?.fil2?.yesterdayRevenue
                }
            }

            override fun onTabUnselected(tab: XTabLayout.Tab?) {
            }

            override fun onTabReselected(tab: XTabLayout.Tab?) {
            }

        })
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_BUY_BROAD) {
            lazyLoad()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
