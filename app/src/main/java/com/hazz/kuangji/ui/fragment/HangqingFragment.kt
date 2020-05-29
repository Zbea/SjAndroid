package com.hazz.kuangji.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.LoginContract
import com.hazz.kuangji.mvp.model.bean.Kuangji
import com.hazz.kuangji.mvp.model.bean.Mingxi
import com.hazz.kuangji.mvp.presenter.KuangjiPresenter
import com.hazz.kuangji.ui.adapter.CoinAdapter
import com.hazz.kuangji.widget.RewardItemDeco
import com.scwang.smartrefresh.layout.util.DensityUtil
import kotlinx.android.synthetic.main.fragment_hangqing.*

class HangqingFragment : BaseFragment(), LoginContract.kuangjiView {
    override fun getMingxi(msg: Mingxi) {

    }

    override fun getKuangji(msg: Kuangji) {

        if(msg.total!=null){
            tv_touzi.text=msg.total
        }
        if(msg.yesterday!=null){
            tv_shouyi.text=msg.yesterday
        }
        mAdapter!!.setNewData(msg.machine_list.list)
    }


    private var mKuangjiPresenter:KuangjiPresenter= KuangjiPresenter(this)
    private var mAdapter: CoinAdapter?=null
    private var list: MutableList<String>? = mutableListOf()
    override fun getLayoutId(): Int {
        return R.layout.fragment_hangqing
    }

    override fun initView() {
        recycle_view.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = CoinAdapter(R.layout.item_kuangji, null)
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



    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            mKuangjiPresenter.kuangji()
        }
    }
}
