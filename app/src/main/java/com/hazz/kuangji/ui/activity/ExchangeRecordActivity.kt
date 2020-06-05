package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.ExchangeRecordBean
import com.hazz.kuangji.mvp.presenter.ExchangeOrderBuyDetailsPresenter
import com.hazz.kuangji.mvp.presenter.ExchangeRecordPresenter
import com.hazz.kuangji.ui.adapter.ExchangeRecordAdapter
import com.hazz.kuangji.ui.adapter.HomeAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.fragment_new_home.*
import kotlinx.android.synthetic.main.rule.mToolBar

class ExchangeRecordActivity : BaseActivity(), IContractView.IExchangeRecordView {

    private var mExchangeRecordPresenter=ExchangeRecordPresenter(this)
    private var type=0;//0买入1卖出
    private var mAdapter: ExchangeRecordAdapter? =null;

    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initView() {

        type=intent.getIntExtra("type",0)

        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(if(type==0)"买币明细" else "卖币明细")
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ExchangeRecordAdapter(R.layout.item_exchange_record, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(15f)
        rc_list.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )


    }

    override fun initData() {

        var exchangeRecordBean=ExchangeRecordBean()
        exchangeRecordBean.orderNumber="111111111111111111"
        exchangeRecordBean.date="2020-6-4 10:50"
        exchangeRecordBean.num=10
        exchangeRecordBean.type=0
        exchangeRecordBean.money="5555.45"
        exchangeRecordBean.state=0
        exchangeRecordBean.stateName="未处理"

        var exchangeRecordBean1=ExchangeRecordBean()
        exchangeRecordBean1.orderNumber="222222222222222"
        exchangeRecordBean1.date="2020-6-4 10:50"
        exchangeRecordBean1.num=10
        exchangeRecordBean1.type=1
        exchangeRecordBean1.money="5555.45"
        exchangeRecordBean1.state=1
        exchangeRecordBean1.stateName="已完成"

        val list= ArrayList<ExchangeRecordBean>();
        list.add(exchangeRecordBean)
        list.add(exchangeRecordBean1)

        mAdapter?.setNewData(list)


    }
    override fun start() {
    }

    override fun setListView(data: ExchangeRecordBean) {

    }


}