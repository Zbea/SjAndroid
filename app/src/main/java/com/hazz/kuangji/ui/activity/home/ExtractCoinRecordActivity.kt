package com.hazz.kuangji.ui.activity.home

import android.content.Intent
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseActivity
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.model.ExtractRule
import com.hazz.kuangji.mvp.presenter.ExtractCoinPresenter
import com.hazz.kuangji.ui.adapter.ExtractCoinRecordAdapter
import com.hazz.kuangji.utils.ToolBarCustom
import kotlinx.android.synthetic.main.activity_list.*


class ExtractCoinRecordActivity : BaseActivity(), IContractView.IExtractView {

    private var mAdapter: ExtractCoinRecordAdapter? = null
    private var mExtractCoinPresenter: ExtractCoinPresenter = ExtractCoinPresenter(this)
    private var page = 1
    private var record: ExtractRecord? = null

    override fun extractSucceed(msg: String) {
    }

    override fun extractRecord(msg: ExtractRecord) {
        if (msg == null) return
        record = msg
        if (page == 1) {
            mAdapter?.setNewData(msg.list)
        } else {
            mAdapter?.addData(msg.list)
        }
        if (msg.list.size < Constants.PAGE_SIZE) {
            mAdapter?.loadMoreEnd(true)
        } else {
            mAdapter?.loadMoreComplete()
        }
    }

    override fun extractRule(msg: ExtractRule) {
        TODO("Not yet implemented")
    }


    override fun layoutId(): Int {
        return R.layout.activity_list
    }

    override fun initData() {
    }

    override fun initView() {
        ToolBarCustom.newBuilder(mToolBar as Toolbar)
            .setTitle(getString(R.string.tibi_record))
            .setOnLeftIconClickListener { finish() }

        rc_list.layoutManager = LinearLayoutManager(this)//创建布局管理
        mAdapter = ExtractCoinRecordAdapter(R.layout.item_extract_coin, null)
        rc_list.adapter = mAdapter
        mAdapter?.bindToRecyclerView(rc_list)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        mAdapter?.setOnLoadMoreListener(BaseQuickAdapter.RequestLoadMoreListener {
            rc_list.postDelayed(Runnable {
                var size = mAdapter?.data?.size
                if (page * Constants.PAGE_SIZE == size) {
                    page += 1
                    mExtractCoinPresenter.record(page.toString())
                }
            }, 1000)
        }, rc_list)
        mAdapter?.setOnItemClickListener { adapter, view, position ->
            startActivity(Intent(this, ExtractCoinRecordDetailsActivity::class.java).putExtra("recordDetails", record?.list?.get(position))
            )

        }

    }

    override fun start() {
        mExtractCoinPresenter.record(page.toString())

    }


}
