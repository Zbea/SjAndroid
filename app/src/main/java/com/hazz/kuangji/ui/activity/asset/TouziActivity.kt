package com.hazz.kuangji.ui.activity.asset

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.Touzi
import com.hazz.kuangji.mvp.model.TouziRecord
import com.hazz.kuangji.mvp.presenter.TouziPresenter
import com.hazz.kuangji.ui.adapter.TouziAdapter
import com.hazz.kuangji.utils.BigDecimalUtil
import com.hazz.kuangji.utils.ToolBarCustom
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.touzi.*


class TouziActivity : BaseActivity(), IContractView.TouziView {
    override fun touziRecord(msg: TouziRecord) {

    }

    override fun touziConfirm(msg: String) {

    }

    override fun touziList(msg: Touzi) {
        tv_level.text = "当前等级:" + msg.level
        tv_current.text = msg.current_invest

        tv1.text = BigDecimalUtil.mul(msg.invest,"1",3)
        tv2.text = BigDecimalUtil.mul(msg.remain,"1",3)
        tv3.text = BigDecimalUtil.mul(msg.revenue.toString(),"1",3)

        val products = msg.products
        mAdapter!!.setNewData(products)

    }

    override fun layoutId(): Int {
        return R.layout.touzi
    }

    override fun initData() {
        mTouziPresenter.touxiList()
    }


    private var mAdapter: TouziAdapter? = null
    private var mTouziPresenter: TouziPresenter = TouziPresenter(this)

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.icon_back)
                .setTitle(getString(R.string.touzi))
                .setTitleColor(resources.getColor(R.color.color_white))
                .setRightText(getString(R.string.touzi_record))
                .setRightTextIsShow(true)
                .setOnLeftIconClickListener { view -> finish() }
                .setOnRightClickListener {
                    startActivity(Intent(this, TouziRecordActivity::class.java))

                }

        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = TouziAdapter(R.layout.item_touzi, null)
        recycle_view.adapter = mAdapter
        mAdapter!!.bindToRecyclerView(recycle_view)
        mAdapter!!.setEmptyView(R.layout.fragment_empty)
        val leftRightOffset = DensityUtil.dp2px(10f)

        recycle_view.addItemDecoration(
                RewardItemDeco(
                        0,
                        0,
                        0,
                        leftRightOffset,
                        0
                )
        )
    }

    override fun start() {

    }


}
