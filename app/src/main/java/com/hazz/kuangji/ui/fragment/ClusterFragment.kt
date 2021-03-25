package com.hazz.kuangji.ui.fragment

import android.content.Intent
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.hazz.kuangji.Constants
import com.hazz.kuangji.R
import com.hazz.kuangji.base.BaseFragment
import com.hazz.kuangji.mvp.contract.IContractView
import com.hazz.kuangji.mvp.model.AssetCluster
import com.hazz.kuangji.mvp.model.AssetClusterEarningsDetails
import com.hazz.kuangji.mvp.model.ExtractRecord
import com.hazz.kuangji.mvp.presenter.AssetClusterPresenter
import com.hazz.kuangji.ui.activity.asset.ExtractFilActivity
import com.hazz.kuangji.ui.adapter.AssetClusterAdapter
import com.hazz.kuangji.utils.DensityUtils
import kotlinx.android.synthetic.main.fragment_cluster.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*


/**
 * @Created by Zbea
 * @fileName ClusterFragment
 * @date 2021/1/27 15:32
 * @email xiaofeng9212@126.com
 * @describe TODO
 **/
class ClusterFragment : BaseFragment() ,IContractView.IAssetClusterView{

    private val assetClusterPresenter=AssetClusterPresenter(this)
    private var mAdapter: AssetClusterAdapter? = null
    private var page=1
    private var assetCluster: AssetCluster?=null

    override fun getCluster(msg: AssetCluster) {
        assetCluster=msg
        sl_refresh?.isRefreshing = false
        if (activity!=null && mView!=null)
        {
            tv_total_storage.text=msg.total_storage
            tv_seal_storage.text=msg.seal_storage
            tv_seal_add.text=msg.seal_add
            tv_release_fil.text=msg.release_fil
            tv_day_t_fil.text=msg.day_t_fil
            tv_day_coinage_fil.text=msg.day_coinage_fil
            tv_unrelease_fil.text=msg.unrelease_fil
            tv_pledge_fil.text=msg.pledge_fil
            tv_day.text=msg.round
        }
    }


    override fun getList(item: AssetClusterEarningsDetails) {
        sl_refresh?.isRefreshing = false
        if (item!=null)
        {
            if (page==1)
            {
                mAdapter?.setNewData(item.list)
            }
            else
            {
                mAdapter?.addData(item.list)
            }
            if (item.list.size<20)
            {
                mAdapter?.loadMoreEnd(true)
            }
            else{
                mAdapter?.loadMoreComplete()
            }
        }
    }

    override fun onSuccess() {
        TODO("Not yet implemented")
    }

    override fun getExtractList(item: ExtractRecord) {
        TODO("Not yet implemented")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_cluster
    }

    override fun initView() {
        EventBus.getDefault().register(this)

        var layoutParams: LinearLayout.LayoutParams= toolbar.layoutParams as LinearLayout.LayoutParams
        layoutParams.topMargin= activity?.let { DensityUtils.getStatusBarHeight(it) }!!
        toolbar.layoutParams=layoutParams

        sl_refresh=activity?.findViewById(R.id.sl_refresh_cluster)
        sl_refresh?.isRefreshing = true
        sl_refresh?.setColorSchemeResources(R.color.color_main)
        sl_refresh?.setOnRefreshListener {
            page=1
            lazyLoad()
        }

        list_cluster.layoutManager = LinearLayoutManager(activity)//创建布局管理
        mAdapter = AssetClusterAdapter(R.layout.item_cluster_earnigs, null)
        list_cluster.adapter = mAdapter
        mAdapter?.bindToRecyclerView(list_cluster)
        mAdapter?.setEmptyView(R.layout.fragment_empty)
        mAdapter?.setOnLoadMoreListener(RequestLoadMoreListener {
            list_cluster.postDelayed(Runnable {
                var size=mAdapter?.data?.size
                if ( page*20==size )
                {
                    page += 1
                    assetClusterPresenter.getClusterEarnings(page.toString())
                }
            }, 1000)
        }, list_cluster)

        tv_make.setOnClickListener {
            if (assetCluster!=null && assetCluster is AssetCluster)
            {
                var intent = Intent(activity, ExtractFilActivity::class.java)
                intent.putExtra("asset", assetCluster)
                startActivity(intent)
            }
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: String) {
        if (event== Constants.CODE_CLUSTER_EXTRACT)
        {
            assetClusterPresenter.getCluster(false)
        }
    }

    override fun lazyLoad() {
        assetClusterPresenter.getCluster(false)
        assetClusterPresenter.getClusterEarnings(page.toString())
    }
    

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}