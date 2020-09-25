package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExchangeRecord
import com.hazz.kuangji.mvp.presenter.ExchangeRecordPresenter
import com.hazz.kuangji.ui.adapter.ExchangeRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_rule.mToolBar
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class ExchangeRecordActivity : BaseActivity(), IContractView.IExchangeRecordView {

    private var mExchangeRecordPresenter = ExchangeRecordPresenter(this)
    private var type = 1;//0全部1买入2卖出
    private var mAdapter: ExchangeRecordAdapter? = null;
    private lateinit var mDate: List<ExchangeRecord>

    override fun setListView(data: List<ExchangeRecord>) {
        mDate = data
        mAdapter?.setNewData(data)
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initView() {

        type = intent.getIntExtra("type", 0)

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setTitle(if (type == 1) "买币明细" else "卖币明细")
                .setToolBarBgRescource(R.color.color_item_bg)
                .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ExchangeRecordAdapter(R.layout.item_exchange_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(RewardItemDeco(0, 0, 0, leftRightOffset, 0))
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            if (mDate[position].is_del==1)
            {
                return@setOnItemClickListener
            }
            if (type == 1) {
                var intent = Intent(this, ExchangeOrderBuyDetailsActivity::class.java)
                intent.putExtra("code", mDate[position].order_code)
                startActivity(intent)
            }
            if (type == 2) {
                var intent = Intent(this, ExchangeOrderSaleDetailsActivity::class.java)
                intent.putExtra("code", mDate[position].order_code)
                startActivity(intent)
            }
        }

    }

    override fun initData() {
        EventBus.getDefault().register(this)
    }

    override fun start() {
        mExchangeRecordPresenter.getList(type.toString())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event=="10002")
        {
            mExchangeRecordPresenter.getList(type.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}