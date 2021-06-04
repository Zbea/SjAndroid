
package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Mill
import com.hazz.kuangji.mvp.model.MillEarningsList
import com.hazz.kuangji.mvp.presenter.MillPresenter
import com.hazz.kuangji.ui.activity.home.MsgListActivity
import com.hazz.kuangji.ui.activity.mill.MillRecordActivity
import com.hazz.kuangji.ui.adapter.MillAccelerateAdapter
import com.hazz.kuangji.ui.adapter.MillAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.DensityUtils
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_investment.*
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.iv_msg
import kotlinx.android.synthetic.main.fragment_mill.list_mill
import kotlinx.android.synthetic.main.fragment_mill.toolbar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.RoundingMode
import java.util.*
import kotlin.collections.ArrayList

class MillFragment : BaseFragment(), IContractView.kuangjiView {

    private var mMillPresenter: MillPresenter = MillPresenter(this)
    private var mAdapter: MillAdapter? = null
    private var mAdapterAccelerate: MillAccelerateAdapter? = null
    private var datas: MutableList<Mill.MachineListBean.ListBean> = ArrayList()

    override fun getEarningsList(msg: MillEarningsList) {
    }

    override fun getMill(msg: Mill) {
        if (mView == null) return
        sl_refresh?.isRefreshing = false
        datas.clear()

        if (msg.total != null) {
            tv_touzi?.text = msg.total
        }
        if (msg.yesterday != null) {
            tv_shouyi?.text = msg.yesterday
        }
        if (msg.machine_list.list!=null)
            datas.addAll(msg.machine_list.list)
        if (msg.machine_list.boost_list!=null)
            datas.addAll(msg.machine_list.boost_list)
        var countNum = "0" //服务器总量
        for (item in datas) {
            countNum = BigDecimalUtil.add(countNum, item.buy_storage, 2, RoundingMode.HALF_UP)
        }

        tv_number?.text = countNum

        mAdapter?.setNewData(msg.machine_list.list)
        mAdapterAccelerate?.setNewData(msg.machine_list.boost_list)

    }



    override fun getLayoutId(): Int {
        return R.layout.fragment_mill
    }

    override fun initView() {

        EventBus.getDefault().register(this)

        var layoutParams: RelativeLayout.LayoutParams = toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams = layoutParams

        sl_refresh = activity?.findViewById(R.id.sl_refresh_mill)
        sl_refresh?.isRefreshing = true
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

        list_mill.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = MillAdapter(R.layout.item_mill, null)
        list_mill.adapter = mAdapter
        mAdapter?.bindToRecyclerView(list_mill)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        list_mill.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        list_mill_accelerate.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapterAccelerate = MillAccelerateAdapter(R.layout.item_mill_accelerate, null)
        list_mill_accelerate.adapter = mAdapterAccelerate
        mAdapterAccelerate?.bindToRecyclerView(list_mill_accelerate)
        mAdapterAccelerate?.setEmptyView(R.layout.fragment_empty)
        list_mill_accelerate.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))

        rg_mill.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.rb_left) {
                list_mill.visibility=View.VISIBLE
                list_mill_accelerate.visibility=View.GONE
            }
            if (checkedId == R.id.rb_right) {
                list_mill.visibility=View.GONE
                list_mill_accelerate.visibility=View.VISIBLE
            }
        }

    }

    override fun lazyLoad() {
        mMillPresenter.kuangji()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event == Constants.CODE_BUY_BROAD) {
            mMillPresenter.kuangji()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
