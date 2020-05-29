package com.hazz.kuangji.ui.activity

import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Touzi
import com.hazz.kuangji.mvp.model.bean.TouziRecord
import com.hazz.kuangji.mvp.presenter.TouziPresenter
import com.hazz.kuangji.ui.adapter.TouziRecordAdapter
import com.hazz.kuangji.utils.*
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.invitefriends_record.*


class TouziRecordActivity : BaseActivity(), LoginContract.TouziView {
    override fun touziList(msg: Touzi) {

    }

    override fun touziConfirm(msg: String) {
    }

    override fun touziRecord(msg: TouziRecord) {
        mAdapter!!.setNewData(msg.list)
    }


    override fun layoutId(): Int {
        return R.layout.tibi_record
    }

    override fun initData() {
        mTouziPresenter.touziRecord()
    }


    private var mAdapter: TouziRecordAdapter?=null
    private var mTouziPresenter: TouziPresenter = TouziPresenter(this)


    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
                .setLeftIcon(R.mipmap.back_white)
                .setTitle(getString(R.string.touzi_record))
                .setToolBarBgRescource(R.drawable.bg_hangqing)
                .setTitleColor(resources.getColor(R.color.color_white))
                .setOnLeftIconClickListener { view -> finish() }


        recycle_view.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = TouziRecordAdapter(R.layout.item_touzi_record, null)
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
