package com.hazz.kuangji.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.bean.Kuangji
import com.hazz.kuangji.mvp.model.bean.Mingxi
import com.hazz.kuangji.mvp.presenter.KuangjiPresenter
import com.hazz.kuangji.ui.adapter.CoinAdapter
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_asset.*
import kotlinx.android.synthetic.main.fragment_mill.*
import kotlinx.android.synthetic.main.fragment_mill.recycle_view
import kotlinx.android.synthetic.main.fragment_mill.sl_refresh
import kotlinx.android.synthetic.main.fragment_mill.tv_shouyi
import kotlinx.android.synthetic.main.fragment_mill.tv_touzi

class MillFragment : BaseFragment(), IContractView.kuangjiView {

    private var mKuangjiPresenter:KuangjiPresenter= KuangjiPresenter(this)
    private var mAdapter: CoinAdapter?=null
    private var list: MutableList<String>? = mutableListOf()

    override fun getMingxi(msg: Mingxi) {

    }

    override fun getKuangji(msg: Kuangji) {
        sl_refresh.isRefreshing=false
        if(msg.total!=null){
            tv_touzi?.text=msg.total
        }
        if(msg.yesterday!=null){
            tv_shouyi?.text=msg.yesterday
        }
        mAdapter?.setNewData(msg.machine_list.list)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_mill
    }

    override fun initView() {

        sl_refresh.isRefreshing=true
        sl_refresh.setColorSchemeResources(R.color.blue)
        sl_refresh.setOnRefreshListener {
            lazyLoad()
        }

        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = CoinAdapter(R.layout.item_mill, null)
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

    override fun lazyLoad() {
        mKuangjiPresenter.kuangji()
    }

    override fun fail(msg: String) {
        super.fail(msg)
        sl_refresh.isRefreshing=false
    }


}
