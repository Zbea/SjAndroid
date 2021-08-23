package com.hazz.kuangji.ui.fragment

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
    private var datas: MutableList<Mill.ListBean> = ArrayList()
    private var datasAccelerate: MutableList<Mill.ListBean> = ArrayList()
    private var mMill: Mill? = null
    private var keyLists = ArrayList<String>()
    private var type = 0
    private var typeMill = 0

    override fun getMill(msg: Mill) {
        if (mView == null) return
        if (msg == null) return

        initTab(msg)

        sl_refresh?.isRefreshing = false
        mMill = msg

        tv_number.text = msg?.fil?.power
        tv_earnings.text = msg?.fil?.totalRevenue
        tv_yesterday.text = msg?.fil?.yesterdayRevenue

        //区分老服务器，集群
        for (item in msg?.fil.list) {
            if (item.minerType == "0") {
                datas.add(item)
            } else {
                datasAccelerate.add(item)
            }
        }
        setListFil()
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
            tab.removeAllTabs()
            lazyLoad()
        }

        val leftRightOffset = DensityUtil.dp2px(5f)
        list.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapterFIL = MillFILAdapter(R.layout.item_mill_fil, null)
        list.adapter = mAdapterFIL
        mAdapterFIL?.bindToRecyclerView(list)
        mAdapterFIL?.setEmptyView(R.layout.fragment_empty)
        list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        rg_mill.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_left) {
                mAdapterFIL?.setNewData(datas)
                typeMill = 0
            }
            if (checkedId == R.id.rb_right) {
                mAdapterFIL?.setNewData(datasAccelerate)
                typeMill = 1
            }
        }
    }

    override fun lazyLoad() {
        tab.removeAllTabs()
        keyLists.clear()
        datas.clear()
        datasAccelerate.clear()
        mMillPresenter.mill()
    }

    //设置矿机数据
    private fun setListFil() {
        //设置刷新后的选中需要显示的类型
        if (typeMill == 0) {
            mAdapterFIL?.setNewData(datas)
        } else {
            mAdapterFIL?.setNewData(datasAccelerate)
        }
    }

    /**
     * 动态获取设置tab索引
     */
    private fun initTab(msg: Mill) {
        if (msg == null) return

        var json = JSONObject(Gson().toJson(msg).toString())
        var it = json.keys()
        while (it.hasNext()) {
            keyLists.add(it.next().toString())
        }
        keyLists.reverse()//倒序
        for (i in keyLists) {
            tab?.newTab()?.setText(i)?.let { it -> tab?.addTab(it) }
        }

        if (keyLists.size > 1) {
            ll_tab.visibility = View.VISIBLE
        } else {
            ll_tab.visibility = View.GONE
        }

        tab?.addOnTabSelectedListener(object : XTabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: XTabLayout.Tab?) {
                if (tab?.text.toString() == "chia") {
                    if (mMill?.chia == null) return
                    rg_mill.visibility = View.GONE
                    mAdapterFIL?.setNewData(mMill?.chia?.list)
                    tv_number?.text = mMill?.chia?.power
                    tv_earnings?.text = mMill?.chia?.totalRevenue
                    tv_yesterday?.text = mMill?.chia?.yesterdayRevenue
                } else if (tab?.text.toString() == "bzz") {
                    if (mMill?.bzz == null) return
                    rg_mill.visibility = View.GONE
                    mAdapterFIL?.setNewData(mMill?.bzz?.list)
                    tv_number?.text = mMill?.bzz?.power
                    tv_earnings?.text = mMill?.bzz?.totalRevenue
                    tv_yesterday?.text = mMill?.bzz?.yesterdayRevenue
                } else {
                    rg_mill.visibility = View.VISIBLE
                    setListFil()
                    tv_number?.text = mMill?.fil?.power
                    tv_earnings?.text = mMill?.fil?.totalRevenue
                    tv_yesterday?.text = mMill?.fil?.yesterdayRevenue
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
